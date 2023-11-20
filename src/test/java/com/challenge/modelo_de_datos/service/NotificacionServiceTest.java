package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Notificacion;
import com.challenge.modelo_de_datos.model.TipoNotificacion;
import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.repository.NotificacionRepository;
import com.challenge.modelo_de_datos.repository.TipoNotificacionRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TipoNotificacionRepository tipoNotificacionRepository;

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Usuario usuario;
    private TipoNotificacion tipoNotificacion;

    private Notificacion notificacion;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        notificacion = new Notificacion();
    }

    @Test
    void getNotificaciones() {
        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(notificacion));
        assertNotNull(notificacionService.getNotificaciones());
    }

    /*new notificacion*/
    @Test
    public void testNewNotificacion_Success(){
        notificacion.setDescripcion("prueba");
        notificacion.setTipoNotificacion(new TipoNotificacion());
        notificacion.setUsuario(new Usuario());
        when(usuarioRepository.existsById(notificacion.getUsuario().getIdUsuario())).thenReturn(true);
        when(tipoNotificacionRepository.existsById(notificacion.getTipoNotificacion().getIdTipoNotificacion())).thenReturn(true);
        when(notificacionRepository.save(notificacion)).thenReturn(notificacion);
        ResponseEntity<Object> response = notificacionService.newNotificacion(notificacion);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardo con exito",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,times(1)).save(notificacion);
    }

    @Test
    public void testNewNotificacion_BadRequest_InvalidID(){
        notificacion.setIdNotificacion(1);
        ResponseEntity<Object> response = notificacionService.newNotificacion(notificacion);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);
    }

    @Test
    public void testNewNotificacion_BadRequest_NullFields(){
        notificacion.setDescripcion(null);
        notificacion.setUsuario(null);
        notificacion.setTipoNotificacion(null);
        ResponseEntity<Object> response = notificacionService.newNotificacion(notificacion);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla excepto el ID",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);

    }

    @Test
    public void testNewNotificacion_Conflict_Blank(){
        notificacion.setDescripcion("");
        notificacion.setTipoNotificacion(new TipoNotificacion());
        notificacion.setUsuario(new Usuario());
        ResponseEntity<Object> response = notificacionService.newNotificacion(notificacion);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de texto no deben estar vacios",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);
    }


    @Test
    public void testUpdateNotificacion_Success(){

        notificacion.setIdNotificacion(1);
        notificacion.setDescripcion("prueba");
        notificacion.setTipoNotificacion(new TipoNotificacion());
        notificacion.setUsuario(new Usuario());
        when(usuarioRepository.existsById(notificacion.getUsuario().getIdUsuario())).thenReturn(true);
        when(tipoNotificacionRepository.existsById(notificacion.getTipoNotificacion().getIdTipoNotificacion())).thenReturn(true);
        when(notificacionRepository.existsById(notificacion.getIdNotificacion())).thenReturn(true);
        when(notificacionRepository.save(notificacion)).thenReturn(notificacion);
        ResponseEntity<Object> response = notificacionService.updateNotificacion(1,notificacion);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se actualizo con exito",((HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testUpdateNotificacion_BadRequest_InvalidId(){
        notificacion.setIdNotificacion(1);
        notificacion.setTipoNotificacion(new TipoNotificacion());
        notificacion.setUsuario(new Usuario());
        notificacion.setDescripcion("prueba");
        ResponseEntity<Object> response = notificacionService.updateNotificacion(1,notificacion);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id proporcionado de la notificacion es erroneo",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);

    }

    @Test
    public void testUpdateNotificacion_BadRequest_NullFields(){
        notificacion.setUsuario(null);
        notificacion.setTipoNotificacion(null);
        ResponseEntity<Object> response = notificacionService.updateNotificacion(1,notificacion);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla excepto el ID",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);

    }

    @Test
    public void testUpdateNotificacion_Conflict_Blank(){
        notificacion.setIdNotificacion(1);
        notificacion.setDescripcion("");
        notificacion.setTipoNotificacion(new TipoNotificacion());
        notificacion.setUsuario(new Usuario());
        when(notificacionRepository.existsById(notificacion.getIdNotificacion())).thenReturn(true);
        ResponseEntity<Object> response = notificacionService.updateNotificacion(1,notificacion);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de texto no deben estar vacios",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);
    }

    @Test
    public void testUpdateNotificacion_Conflict_Numeric(){
        notificacion.setIdNotificacion(1);
        notificacion.setDescripcion("123");
        notificacion.setTipoNotificacion(new TipoNotificacion());
        notificacion.setUsuario(new Usuario());
        when(notificacionRepository.existsById(notificacion.getIdNotificacion())).thenReturn(true);
        ResponseEntity<Object> response = notificacionService.updateNotificacion(1,notificacion);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de texto no deben ser numeros",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);
    }

    @Test
    public void testUpdateNotificacion_IdTipoNotificacion(){
        notificacion.setIdNotificacion(1);
        notificacion.setDescripcion("prueba");
        notificacion.setTipoNotificacion(new TipoNotificacion());
        notificacion.setUsuario(new Usuario());

        when(notificacionRepository.existsById(notificacion.getIdNotificacion())).thenReturn(true);
        when(tipoNotificacionRepository.existsById(notificacion.getUsuario().getIdUsuario())).thenReturn(true);
        when(tipoNotificacionRepository.existsById(notificacion.getTipoNotificacion().getIdTipoNotificacion())).thenReturn(false);
        ResponseEntity<Object> response = notificacionService.updateNotificacion(1,notificacion);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El tipo de notificacion no existe,ID erroneo",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);
    }

    @Test
    public void testUpdateNotificacion_IdUsuario(){
        notificacion.setIdNotificacion(1);
        notificacion.setDescripcion("prueba");
        notificacion.setTipoNotificacion(new TipoNotificacion());
        notificacion.setUsuario(new Usuario());
        when(notificacionRepository.existsById(notificacion.getIdNotificacion())).thenReturn(true);
        when(tipoNotificacionRepository.existsById(notificacion.getTipoNotificacion().getIdTipoNotificacion())).thenReturn(true);
        when(usuarioRepository.existsById(notificacion.getUsuario().getIdUsuario())).thenReturn(false);
        ResponseEntity<Object> response = notificacionService.updateNotificacion(1,notificacion);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El usuario no existe,ID erroneo",((HashMap) response.getBody()).get("message"));
        verify(notificacionRepository,never()).save(notificacion);
    }

    @Test
    void deleteNotificacion(){
        notificacion.setIdNotificacion(1);
        when(notificacionRepository.existsById(notificacion.getIdNotificacion())).thenReturn(true);
        ResponseEntity<Object> response = notificacionService.deleteNotificacion(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Notificacion eliminada",((HashMap) response.getBody()).get("message"));

    }

    @Test
    void deleteNotificacion_error(){
        notificacion.setIdNotificacion(1);
        when(notificacionRepository.existsById(notificacion.getIdNotificacion())).thenReturn(false);
        ResponseEntity<Object> response = notificacionService.deleteNotificacion(1);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No existe la notificacion con ese id",((HashMap) response.getBody()).get("message"));
    }



}
package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.ResenaTienda;
import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.model.Vendedor;
import com.challenge.modelo_de_datos.repository.ResenaTiendaRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResenaTiendaServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private ResenaTiendaRepository resenaTiendaRepository;

    @InjectMocks
    private ResenaTiendaService resenaTiendaService;

    private Usuario usuario;
    private Vendedor vendedor;
    private ResenaTienda resenaTienda;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        resenaTienda = new ResenaTienda();
    }

    @Test
    void getResenasTienda() {
        when(resenaTiendaRepository.findAll()).thenReturn(Arrays.asList(resenaTienda));
        assertNotNull(resenaTiendaService.getResenasTienda());
    }

    @Test
    public void testNewResenaTienda_Success() {
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        when(resenaTiendaRepository.save(resenaTienda)).thenReturn(resenaTienda);
        ResponseEntity<Object> response = resenaTiendaService.newResenaTienda(resenaTienda);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("SE GUARDO RESEÑA CON EXITO", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(1)).save(resenaTienda);
    }

    @Test
    public void testNewResenaTienda_BadRequest_InvalidId() {
        resenaTienda.setIdResenaTienda(1);
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        ResponseEntity<Object> response = resenaTiendaService.newResenaTienda(resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testNewResenaTienda_BadRequest_InvalidUsuario() {
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(false);
        ResponseEntity<Object> response = resenaTiendaService.newResenaTienda(resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El usuario no existe, ingrese un ID valido", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testNewResenaTienda_BadRequest_InvalidVendedor() {
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());

        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(false);
        ResponseEntity<Object> response = resenaTiendaService.newResenaTienda(resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El vendedor no existe, ingrese un ID valido", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testNewResenaTienda_BadRequest_NullFields() {
        resenaTienda.setDescripcion(null);
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(null);
        resenaTienda.setVendedor(null);
        ResponseEntity<Object> response = resenaTiendaService.newResenaTienda(resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }



    @Test
    public void testNewResenaTienda_Conflict_Numeric(){
        resenaTienda.setDescripcion("123");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        ResponseEntity<Object> response=resenaTiendaService.newResenaTienda(resenaTienda);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros",((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testNewResenaTienda_Conflict_Blank(){
        resenaTienda.setDescripcion("");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        ResponseEntity<Object> response=resenaTiendaService.newResenaTienda(resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("El campo descripcion no puede ser nulo",((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testNewResenaTienda_Conflict_Negative(){
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(-1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        ResponseEntity<Object> response=resenaTiendaService.newResenaTienda(resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("La calificacion debe ser positiva.",((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testUpdateResenaTienda_Success(){
        resenaTienda.setIdResenaTienda(1);
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        when(resenaTiendaRepository.save(resenaTienda)).thenReturn(resenaTienda);
        ResponseEntity<Object> response = resenaTiendaService.updateResenaTienda(1,resenaTienda);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("SE ACTUALIZO RESEÑA CON EXITO", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(1)).save(resenaTienda);
    }

    @Test
    public void testUpdateResenaTienda_BadRequest_InvalidId(){
        resenaTienda.setIdResenaTienda(1);
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        ResponseEntity<Object> response = resenaTiendaService.updateResenaTienda(1,resenaTienda);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("La reseña no existe", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testUpdateResenaTienda_BadRequest_NullFields(){
        resenaTienda.setDescripcion(null);
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(null);
        resenaTienda.setVendedor(null);
        ResponseEntity<Object> response = resenaTiendaService.updateResenaTienda(1,resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testUpdateResenaTienda_Conflict_Numeric(){
        resenaTienda.setIdResenaTienda(1);
        resenaTienda.setDescripcion("123");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        ResponseEntity<Object> response=resenaTiendaService.updateResenaTienda(1,resenaTienda);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros",((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testUpdateResenaTienda_Conflict_Blank(){
        resenaTienda.setIdResenaTienda(1);
        resenaTienda.setDescripcion("");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        ResponseEntity<Object> response=resenaTiendaService.updateResenaTienda(1,resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("El campo descripcion no puede ser nulo",((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testUpdateResenaTienda_Conflict_Negative(){
        resenaTienda.setIdResenaTienda(1);
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(-1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        ResponseEntity<Object> response=resenaTiendaService.updateResenaTienda(1,resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("La calificacion debe ser positiva.",((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testUpdateResenaTienda_BadRequest_InvalidUsuario() {
        resenaTienda.setIdResenaTienda(1);
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());

        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);

        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(false);
        ResponseEntity<Object> response = resenaTiendaService.updateResenaTienda(1,resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El usuario no existe, ingrese un ID valido", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void testUpdateResenaTienda_BadRequest_InvalidVendedor() {
        resenaTienda.setIdResenaTienda(1);
        resenaTienda.setDescripcion("prueba");
        resenaTienda.setCalificacionTienda(1);
        resenaTienda.setUsuario(new Usuario());
        resenaTienda.setVendedor(new Vendedor());

        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        when(usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())).thenReturn(false);
        ResponseEntity<Object> response = resenaTiendaService.updateResenaTienda(1,resenaTienda);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El vendedor no existe, ingrese un ID valido", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).save(resenaTienda);
    }

    @Test
    public void deleteResenaTienda_error(){
        resenaTienda.setIdResenaTienda(1);
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(false);
        ResponseEntity<Object> response = resenaTiendaService.deleteResenaTieneda(1);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No hay resenaTienda con ese id", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(0)).deleteById(resenaTienda.getIdResenaTienda());
    }

    @Test
    public void deleteResenaTienda() {
        resenaTienda.setIdResenaTienda(1);
        when(resenaTiendaRepository.existsById(resenaTienda.getIdResenaTienda())).thenReturn(true);
        ResponseEntity<Object> response = resenaTiendaService.deleteResenaTieneda(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("resenaTienda eliminada", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaTiendaRepository, times(1)).deleteById(resenaTienda.getIdResenaTienda());
    }




}
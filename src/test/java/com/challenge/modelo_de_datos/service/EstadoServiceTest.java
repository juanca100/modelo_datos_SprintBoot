package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.*;
import com.challenge.modelo_de_datos.repository.EstadoRepository;
import com.challenge.modelo_de_datos.repository.PaisRepository;
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

class EstadoServiceTest {
    @Mock
    private EstadoRepository estadoRepository;
    @Mock
    private PaisRepository paisRepository;

    @InjectMocks
    private EstadoService estadoService;

    private Estado estado;
    private Pais pais;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        estado = new Estado();
        estado.setEstado("prueba");
        estado.setPais(new Pais());
    }

    @Test
    void getEstados() {
        when(estadoRepository.findAll()).thenReturn(Arrays.asList(estado));
        assertNotNull(estadoService.getEstados());
    }
    /*new estado*/
    @Test
    public void testNewEstado_Success() {
        estado.setEstado("prueba");
        estado.setPais(new Pais());
        when(paisRepository.existsById(estado.getPais().getIdPais())).thenReturn(true);
        when(estadoRepository.save(estado)).thenReturn(estado);
        ResponseEntity<Object> response = estadoService.newEstado(estado);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, times(1)).save(estado);
    }
    @Test
    public void testNewEstado_BadRequest_InvalidId() {
        estado.setIdEstado(1);
        ResponseEntity<Object> response = estadoService.newEstado(estado);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    @Test
    public void testNewEstado_BadRequest_NullFields() {
        estado.setEstado(null);
        estado.setPais(null);
        ResponseEntity<Object> response = estadoService.newEstado(estado);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    @Test
    public void testNewEstado_Conflict_Blank() {
        estado.setEstado("");
        ResponseEntity<Object> response = estadoService.newEstado(estado);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    @Test
    public void testNewEstado_Conflict_Numeric() {
        estado.setEstado("123");
        ResponseEntity<Object> response = estadoService.newEstado(estado);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    @Test
    public void testNewEstado_IdPais_Invalid(){
        estado.setPais(new Pais());
        when(paisRepository.existsById(estado.getPais().getIdPais())).thenReturn(false);
        ResponseEntity<Object> response=estadoService.newEstado(estado);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals("El pais no existe, ID erroneo",((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);

    }
    /*fin estado*/
    /*update estado*/
    @Test
    public void testUpdateEstado_Success() {
        estado.setIdEstado(1);
        estado.setEstado("prueba");
        estado.setPais(new Pais());
        when(estadoRepository.existsById(estado.getIdEstado())).thenReturn(true);
        when(paisRepository.existsById(estado.getPais().getIdPais())).thenReturn(true);
        when(estadoRepository.save(estado)).thenReturn(estado);
        ResponseEntity<Object> response = estadoService.updateEstado(1,estado);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se actualizo con exito", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, times(1)).save(estado);
    }
    @Test
    public void testUpdateEstado_BadRequest_InvalidId() {
        estado.setIdEstado(1);
        ResponseEntity<Object> response = estadoService.updateEstado(1,estado);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id del estado proporcionado es erroneo", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    @Test
    public void testUpdateEstado_BadRequest_NullFields() {
        estado.setEstado(null);
        estado.setPais(null);
        ResponseEntity<Object> response = estadoService.updateEstado(1,estado);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    @Test
    public void testUpdateEstado_Conflict_Blank() {
        estado.setIdEstado(1);
        estado.setEstado("");
        when(estadoRepository.existsById(estado.getIdEstado())).thenReturn(true);
        ResponseEntity<Object> response = estadoService.updateEstado(1,estado);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    @Test
    public void testUpdateEstado_Conflict_Numeric() {
        estado.setIdEstado(1);
        estado.setEstado("123");
        when(estadoRepository.existsById(estado.getIdEstado())).thenReturn(true);
        ResponseEntity<Object> response = estadoService.updateEstado(1,estado);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    @Test
    public void testUpdateEstado_IdPais_Invalid(){
        estado.setIdEstado(1);
        estado.setEstado("prueba");
        estado.setPais(new Pais());
        when(estadoRepository.existsById(estado.getIdEstado())).thenReturn(true);
        when(paisRepository.existsById(estado.getPais().getIdPais())).thenReturn(false);
        ResponseEntity<Object> response=estadoService.updateEstado(1,estado);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El pais no existe,ID erroneo",((HashMap) response.getBody()).get("message"));
        verify(estadoRepository, never()).save(estado);
    }
    /*fin estado*/
    /*delete estado*/
    @Test
    void deleteEstado() {
        estado.setIdEstado(1);
        when(estadoRepository.existsById(estado.getIdEstado())).thenReturn(true);
        ResponseEntity<Object> response =estadoService.deleteEstado(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Estado eliminado",((HashMap) response.getBody()).get("message"));
    }
    @Test
    void deleteEstado_error(){
        estado.setIdEstado(1);
        when(estadoRepository.existsById(estado.getIdEstado())).thenReturn(false);
        ResponseEntity<Object> response=estadoService.deleteEstado(1);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("No existe estado con ese id",((HashMap) response.getBody()).get("message"));
    }

    /*fin delete estado*/
}
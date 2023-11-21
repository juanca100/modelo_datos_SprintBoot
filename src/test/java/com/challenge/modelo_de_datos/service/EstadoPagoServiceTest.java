package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.EstadoPago;
import com.challenge.modelo_de_datos.repository.EstadoPagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EstadoPagoServiceTest {
    @Mock
    private EstadoPagoRepository estadoPagoRepository;

    @InjectMocks
    private EstadoPagoService estadoPagoService;

    private EstadoPago estadoPago;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        estadoPago = new EstadoPago();
        estadoPago.setEstadoPago("prueba");
    }

    @Test
    void getEstadosPagos() {
        when(estadoPagoRepository.findAll()).thenReturn(Arrays.asList(estadoPago));
        assertNotNull(estadoPagoService.getEstadosPago());
    }

    @Test
    void testNewEstadosPagos_Success(){
        estadoPago.setEstadoPago("prueba");
        estadoPago.setIdEstadoPago(0);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito",((HashMap) response.getBody()).get("message"));
        verify(estadoPagoRepository,times(1)).save(estadoPago);
    }

    @Test
    void testNewEstadosPagos_NullFileds(){
        estadoPago.setEstadoPago(null);
        estadoPago.setIdEstadoPago(0);
        ResponseEntity<Object> response = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla",((HashMap) response.getBody()).get("message"));
        verify(estadoPagoRepository,never()).save(estadoPago);
    }

    @Test
    void testNewEstadosPagos_IdError(){
        estadoPago.setIdEstadoPago(1);
        ResponseEntity<Object> response = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente",((HashMap) response.getBody()).get("message"));
        verify(estadoPagoRepository,never()).save(estadoPago);
    }

    @Test
    void testNewEstadoPagos_(){
        estadoPago.setEstadoPago("Pagado");
        estadoPago.setIdEstadoPago(1);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente",((HashMap) response.getBody()).get("message"));
        verify(estadoPagoRepository,never()).save(estadoPago);
    }

    @Test
    void testNewEstadoPagos_BlankFields(){
        estadoPago.setEstadoPago("");
        estadoPago.setIdEstadoPago(0);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios",((HashMap) response.getBody()).get("message"));
        verify(estadoPagoRepository,never()).save(estadoPago);
    }

    @Test
    void testNewEstadoPagos_NoNumeric(){
        estadoPago.setEstadoPago("5");
        estadoPago.setIdEstadoPago(0);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros",((HashMap) response.getBody()).get("message"));
        verify(estadoPagoRepository,never()).save(estadoPago);
    }

    @Test
    void testUpdateEstadosPagos_Success(){
        estadoPago.setEstadoPago("prueba");
        estadoPago.setIdEstadoPago(1);
        when(estadoPagoRepository.existsById(estadoPago.getIdEstadoPago())).thenReturn(true);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.updateEstadoPago(1, estadoPago);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se actualizo con éxito",((HashMap) response.getBody()).get("message"));
    }

    @Test
    void testUpdateEstadosPagos_NullFileds(){
        estadoPago.setEstadoPago(null);
        ResponseEntity<Object> response = estadoPagoService.updateEstadoPago(1, estadoPago);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla",((HashMap) response.getBody()).get("message"));
    }

    @Test
    void testUpdateEstadosPagos_IdError(){
        estadoPago.setEstadoPago("prueba");
        estadoPago.setIdEstadoPago(0);
        when(estadoPagoRepository.existsById(estadoPago.getIdEstadoPago())).thenReturn(true);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.updateEstadoPago(1, estadoPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id proporcionado es erroneo",((HashMap) response.getBody()).get("message"));
    }

    @Test
    void testUpdateEstadosPagos_BlankFileds(){
        estadoPago.setEstadoPago("");
        estadoPago.setIdEstadoPago(1);
        when(estadoPagoRepository.existsById(estadoPago.getIdEstadoPago())).thenReturn(true);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.updateEstadoPago(1, estadoPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios",((HashMap) response.getBody()).get("message"));
    }

    @Test
    void testUpdateEstadosPagos_NoNumeric(){
        estadoPago.setEstadoPago("5");
        estadoPago.setIdEstadoPago(1);
        when(estadoPagoRepository.existsById(estadoPago.getIdEstadoPago())).thenReturn(true);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.updateEstadoPago(1, estadoPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros",((HashMap) response.getBody()).get("message"));
    }

    @Test
    void testDeleteEstadosPagos(){
        estadoPago.setEstadoPago("5");
        estadoPago.setIdEstadoPago(1);
        when(estadoPagoRepository.existsById(estadoPago.getIdEstadoPago())).thenReturn(true);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.deleteEstadoPago(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("EstadoPago eliminado",((HashMap) response.getBody()).get("message"));
    }

    @Test
    void testDeleteEstadosPagos_ErrorID(){
        estadoPago.setEstadoPago("5");
        estadoPago.setIdEstadoPago(1);
        when(estadoPagoRepository.existsById(estadoPago.getIdEstadoPago())).thenReturn(false);
        when(estadoPagoRepository.save(estadoPago)).thenReturn(estadoPago);
        ResponseEntity<Object> response = estadoPagoService.deleteEstadoPago(1);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No existe el EstadoPago con ese ID",((HashMap) response.getBody()).get("message"));
    }

}
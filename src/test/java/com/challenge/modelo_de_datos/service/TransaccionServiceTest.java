package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.*;
import com.challenge.modelo_de_datos.repository.CompradorRepository;
import com.challenge.modelo_de_datos.repository.TransaccionRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionServiceTest {
    @Mock
    private TransaccionRepository transaccionRepository;
    @Mock
    private VendedorRepository vendedorRepository;
    @Mock
    private CompradorRepository compradorRepository;
    @InjectMocks
    private TransaccionService  transaccionService;
    private Transaccion transaccion;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transaccion= new Transaccion();
    }

    @Test
    void getTransacciones() {
        when(transaccionRepository.findAll()).thenReturn(Arrays.asList(transaccion));
        assertNotNull(transaccionService.getTransacciones());
    }
    /*New Transaccion*/
    @Test
    public void testNewTransaccion_Success() {
        transaccion.setComprador(new Comprador());
        transaccion.setVendedor(new Vendedor());
        transaccion.setFecha(new Date(12-12-2020));

        when(compradorRepository.existsById(transaccion.getComprador().getIdComprador())).thenReturn(true);
        when(vendedorRepository.existsById(transaccion.getVendedor().getIdVendedor())).thenReturn(true);
        when(transaccionRepository.save(transaccion)).thenReturn(transaccion);
        ResponseEntity<Object> response = transaccionService.newTransaccion(transaccion);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, times(1)).save(transaccion);
    }
    @Test
    public void testNewTransaccion_BadRequest_InvalidId() {
        transaccion.setIdTransaccion(1);
        ResponseEntity<Object> response = transaccionService.newTransaccion(transaccion);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente", ((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, never()).save(transaccion);
    }
    @Test
    public void testNewTransaccion_BadRequest_NullFields() {
        transaccion.setVendedor(null);
        transaccion.setComprador(null);
        transaccion.setFecha(null);
        ResponseEntity<Object> response = transaccionService.newTransaccion(transaccion);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos requeridos de la tabla", ((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, never()).save(transaccion);
    }
    @Test
    public void testNewTransaccion_IdVendedor_Invalid(){
        transaccion.setComprador(new Comprador());
        transaccion.setVendedor(new Vendedor());
        transaccion.setFecha(new Date(12-12-2023));
        when(compradorRepository.existsById(transaccion.getComprador().getIdComprador())).thenReturn(true);
        when(vendedorRepository.existsById(transaccion.getVendedor().getIdVendedor())).thenReturn(false);
        ResponseEntity<Object> response=transaccionService.newTransaccion(transaccion);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El vendedor no existe",((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, never()).save(transaccion);
    }
    @Test
    public void testNewTransaccion_IdComprador_Invalid(){
        transaccion.setComprador(new Comprador());
        transaccion.setVendedor(new Vendedor());
        transaccion.setFecha(new Date(12-12-2023));
        when(compradorRepository.existsById(transaccion.getComprador().getIdComprador())).thenReturn(false);
        ResponseEntity<Object> response=transaccionService.newTransaccion(transaccion);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El comprador no existe",((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, never()).save(transaccion);
    }
    /*fin transaccion*/
    /*update transaccion*/
    @Test
    public void testUpdateTransaccion_Success() {
        transaccion.setIdTransaccion(1);
        transaccion.setComprador(new Comprador());
        transaccion.setVendedor(new Vendedor());
        transaccion.setFecha(new Date(12-12-2020));
        when(transaccionRepository.existsById(transaccion.getIdTransaccion())).thenReturn(true);
        when(compradorRepository.existsById(transaccion.getComprador().getIdComprador())).thenReturn(true);
        when(vendedorRepository.existsById(transaccion.getVendedor().getIdVendedor())).thenReturn(true);
        when(transaccionRepository.save(transaccion)).thenReturn(transaccion);
        ResponseEntity<Object> response = transaccionService.updateTransaccion(1,transaccion);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Se actualizó con éxito", ((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, times(1)).save(transaccion);
    }

    @Test
    public void testUpdateTransaccion_BadRequest_NullFields() {
        transaccion.setVendedor(null);
        transaccion.setComprador(null);
        transaccion.setFecha(null);
        ResponseEntity<Object> response = transaccionService.updateTransaccion(1,transaccion);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos requeridos de la tabla", ((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, never()).save(transaccion);
    }
    @Test
    public void testUpdateTransaccion_IdVendedor_Invalid(){
        transaccion.setIdTransaccion(1);
        transaccion.setComprador(new Comprador());
        transaccion.setVendedor(new Vendedor());
        transaccion.setFecha(new Date(12-12-2023));
        when(transaccionRepository.existsById(transaccion.getIdTransaccion())).thenReturn(true);
        when(compradorRepository.existsById(transaccion.getComprador().getIdComprador())).thenReturn(true);
        when(vendedorRepository.existsById(transaccion.getVendedor().getIdVendedor())).thenReturn(false);
        ResponseEntity<Object> response=transaccionService.updateTransaccion(1,transaccion);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El vendedor no existe",((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, never()).save(transaccion);
    }
    @Test
    public void testUpdateTransaccion_IdComprador_Invalid(){
        transaccion.setIdTransaccion(1);
        transaccion.setComprador(new Comprador());
        transaccion.setVendedor(new Vendedor());
        transaccion.setFecha(new Date(12-12-2023));
        when(transaccionRepository.existsById(transaccion.getIdTransaccion())).thenReturn(true);
        when(compradorRepository.existsById(transaccion.getComprador().getIdComprador())).thenReturn(false);
        ResponseEntity<Object> response=transaccionService.updateTransaccion(1,transaccion);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El comprador no existe",((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, never()).save(transaccion);
    }
    @Test
    public void testUpdateTransaccion_IdTransaccion_Invalid(){
        transaccion.setIdTransaccion(1);
        transaccion.setComprador(new Comprador());
        transaccion.setVendedor(new Vendedor());
        transaccion.setFecha(new Date(12-12-2023));
        when(transaccionRepository.existsById(transaccion.getIdTransaccion())).thenReturn(false);
        ResponseEntity<Object> response=transaccionService.updateTransaccion(1,transaccion);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("No existe la Transaccion con ese ID",((HashMap) response.getBody()).get("message"));
        verify(transaccionRepository, never()).save(transaccion);
    }
    /*fin transaccion*/
    /*delete transaccion*/
    @Test
    void deleteTransaccion() {
        transaccion.setIdTransaccion(1);
        when(transaccionRepository.existsById(transaccion.getIdTransaccion())).thenReturn(true);
        ResponseEntity<Object> response =transaccionService.deleteTransaccion(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Transaccion eliminada",((HashMap) response.getBody()).get("message"));
    }
    @Test
    void deleteTransaccion_error(){
        transaccion.setIdTransaccion(1);
        when(transaccionRepository.existsById(transaccion.getIdTransaccion())).thenReturn(false);
        ResponseEntity<Object> response=transaccionService.deleteTransaccion(1);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("No existe la Transaccion con ese ID",((HashMap) response.getBody()).get("message"));
    }
    /*fin delete transaccion*/

}
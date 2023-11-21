package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Producto;
import com.challenge.modelo_de_datos.model.Transaccion;
import com.challenge.modelo_de_datos.model.TransaccionProducto;
import com.challenge.modelo_de_datos.model.TransaccionProductoId;
import com.challenge.modelo_de_datos.repository.ProductoRepository;
import com.challenge.modelo_de_datos.repository.TransaccionProductoRepository;
import com.challenge.modelo_de_datos.repository.TransaccionRepository;
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
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class TransaccionProductoServiceTest {
    @InjectMocks
    private TransaccionProductoService transaccionProductoService;

    @Mock
    private TransaccionProductoRepository transaccionProductoRepository;

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private ProductoRepository productoRepository;

    private TransaccionProducto transaccionProducto;
    private Transaccion transaccion;
    private Producto producto;
    private TransaccionProductoId id;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transaccionProducto=new TransaccionProducto();
    }

    @Test
    public void getTransaccionesProducto(){
        when(transaccionProductoRepository.findAll()).thenReturn(Arrays.asList(transaccionProducto));
        assertNotNull(transaccionProductoService.getTransaccionesProducto());
    }

    @Test
    public void testNewTransaccionProducto(){
        //Configuraciones Previas
        transaccionProducto.setTransaccion(new Transaccion());
        transaccionProducto.getTransaccion().setIdTransaccion(2);
        transaccionProducto.setProducto(new Producto());
        transaccionProducto.getProducto().setIdProducto(2);
        ResponseEntity<Object> response;


        //El id ya existe
        when(transaccionProductoRepository.existsById(any(TransaccionProductoId.class))).thenReturn(true);
        response=transaccionProductoService.newTransaccionProducto(transaccionProducto);
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Esta transaccion ya se guardo en la base de datos", ((HashMap) response.getBody()).get("message"));

        //Campos Nulos
        when(transaccionProductoRepository.existsById(any(TransaccionProductoId.class))).thenReturn(false);
        response=transaccionProductoService.newTransaccionProducto(transaccionProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));

        transaccionProducto.setCantidad(10);
        //Comprobar que exista el ID de transacion
        when(transaccionRepository.existsById(anyInt())).thenReturn(false);
        response=transaccionProductoService.newTransaccionProducto(transaccionProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("La transaccion no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        //Comprobar que exista el ID de producto
        when(transaccionRepository.existsById(anyInt())).thenReturn(true);
        when(productoRepository.existsById(anyInt())).thenReturn(false);
        response=transaccionProductoService.newTransaccionProducto(transaccionProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El producto no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        //Funcionar
        when(productoRepository.existsById(anyInt())).thenReturn(true);
        response=transaccionProductoService.newTransaccionProducto(transaccionProducto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));

    }

    @Test
    public void testUpdateTransaccionProducto(){
        //Configuraciones Previas
        ResponseEntity<Object> response;

        //Campos Nulos
        response=transaccionProductoService.updateTransaccionProducto(2,2,transaccionProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));

        //Configuraciones
        transaccionProducto.setTransaccion(new Transaccion());
        transaccionProducto.setProducto(new Producto());
        transaccionProducto.setCantidad(10);

        //Comprobar que exista el ID de transacion
        when(transaccionRepository.existsById(anyInt())).thenReturn(false);
        response=transaccionProductoService.updateTransaccionProducto(2,2,transaccionProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("La transaccion no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        //Comprobar que exista el ID del producto
        when(transaccionRepository.existsById(anyInt())).thenReturn(true);
        when(productoRepository.existsById(anyInt())).thenReturn(false);
        response=transaccionProductoService.updateTransaccionProducto(2,2,transaccionProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El producto no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        //No Funcionar
        when(productoRepository.existsById(anyInt())).thenReturn(true);
        when(transaccionProductoRepository.existsById(any(TransaccionProductoId.class))).thenReturn(false);
        response=transaccionProductoService.updateTransaccionProducto(2,2,transaccionProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No existe la TransaccionProducto con ese ID", ((HashMap) response.getBody()).get("message"));

        //Funcionar
        when(transaccionProductoRepository.existsById(any(TransaccionProductoId.class))).thenReturn(true);
        response=transaccionProductoService.updateTransaccionProducto(2,2,transaccionProducto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se actualizó con éxito", ((HashMap) response.getBody()).get("message"));

    }

    @Test
    public void testDeleteTransaccionProducto(){
        //Configuraciones Previas
        ResponseEntity<Object> response;

        //Comprobar que exista el ID de transacion
        when(transaccionRepository.existsById(anyInt())).thenReturn(false);
        response=transaccionProductoService.deleteTransaccionProducto(2,2);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("La transaccion no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        //Comprobar que exista el ID del producto
        when(transaccionRepository.existsById(anyInt())).thenReturn(true);
        when(productoRepository.existsById(anyInt())).thenReturn(false);
        response=transaccionProductoService.deleteTransaccionProducto(2,2);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El producto no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        //No Funcionar
        when(productoRepository.existsById(anyInt())).thenReturn(true);
        when(transaccionProductoRepository.existsById(any(TransaccionProductoId.class))).thenReturn(false);
        response=transaccionProductoService.deleteTransaccionProducto(2,2);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No existe la TransaccionProducto con ese ID", ((HashMap) response.getBody()).get("message"));

        //Funcionar
        when(transaccionProductoRepository.existsById(any(TransaccionProductoId.class))).thenReturn(true);
        response=transaccionProductoService.deleteTransaccionProducto(2,2);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("TransaccionProducto eliminada", ((HashMap) response.getBody()).get("message"));

    }

}
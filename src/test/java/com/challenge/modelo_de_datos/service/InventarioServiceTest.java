package com.challenge.modelo_de_datos.service;


import com.challenge.modelo_de_datos.model.Inventario;
import com.challenge.modelo_de_datos.model.Producto;
import com.challenge.modelo_de_datos.repository.InventarioRepository;
import com.challenge.modelo_de_datos.repository.ProductoRepository;
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

public class InventarioServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    private Producto producto;
    private Inventario inventario;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        inventario = new Inventario();
    }


    @Test
    void getInventario(){
        when(inventarioRepository.findAll()).thenReturn(Arrays.asList(inventario));
        assertNotNull(inventarioService.getInventarios());
    }

    @Test
    public void testNewInventario_Success(){
        inventario.setSalida(5);
        inventario.setEntrada(10);
        inventario.setStockMinimo(30);
        inventario.setStockInicial(30);
        inventario.setProducto(new Producto());
        when(productoRepository.existsById(inventario.getProducto().getIdProducto())).thenReturn(true);
        when(inventarioRepository.save(inventario)).thenReturn(inventario);
        ResponseEntity<Object> response = inventarioService.newInventario(inventario);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito",((HashMap) response.getBody()).get("message"));
        verify(inventarioRepository,times(1)).save(inventario);
    }

    @Test
    public void testNewInventario_BadRequest_Invalid(){
        inventario.setIdInventario(1);
        ResponseEntity<Object> response = inventarioService.newInventario(inventario);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente", ((HashMap) response.getBody()).get("message"));
        verify(inventarioRepository,never()).save(inventario);

    }


    @Test
    public void testNewInventario_BadRequest_NullFields(){
        inventario.setSalida(5);
        inventario.setEntrada(10);
        inventario.setStockMinimo(30);
        inventario.setStockInicial(30);
        inventario.setProducto(new Producto());
        when(productoRepository.existsById(inventario.getProducto().getIdProducto())).thenReturn(false);
        when(inventarioRepository.save(inventario)).thenReturn(inventario);
        ResponseEntity<Object> response = inventarioService.newInventario(inventario);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("El producto no existe",((HashMap) response.getBody()).get("message"));
        verify(inventarioRepository,never()).save(inventario);
    }

    @Test
    public void testUpdateInventario_Sucess(){
        inventario.setSalida(1);
        inventario.setEntrada(1);
        inventario.setStockMinimo(1);
        inventario.setStockInicial(1);
        inventario.setIdInventario(1);
        inventario.setProducto(new Producto());
        when(productoRepository.existsById(inventario.getProducto().getIdProducto())).thenReturn(true);
        when(inventarioRepository.existsById(inventario.getIdInventario())).thenReturn(true);
        when(inventarioRepository.save(inventario)).thenReturn(inventario);
        ResponseEntity<Object> response = inventarioService.updateInventario(1,inventario);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testUpdateInventario_IdProductoInvalid() {
        inventario.setIdInventario(1);
        inventario.setSalida(1);
        inventario.setEntrada(1);
        inventario.setStockMinimo(1);
        inventario.setStockInicial(1);
        inventario.setProducto(new Producto());
        when(inventarioRepository.existsById(inventario.getProducto().getIdProducto())).thenReturn(true);
        when(inventarioRepository.save(inventario)).thenReturn(inventario);
        ResponseEntity<Object> response = inventarioService.updateInventario(1,inventario);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id proporcionado es erroneo", ((HashMap) response.getBody()).get("message"));
        verify(inventarioRepository, never()).save(inventario);
    }

    @Test
    public void testDeleteInventario (){
        inventario.setIdInventario(1);
        when(inventarioRepository.existsById(inventario.getIdInventario())).thenReturn(true);
        ResponseEntity<Object> response = inventarioService.deleteInventario(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Inventario eliminado",((HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testDeleteInventario_error(){
        inventario.setIdInventario(1);
        when(inventarioRepository.existsById(inventario.getIdInventario())).thenReturn(false);
        ResponseEntity<Object> response = inventarioService.deleteInventario(1);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No existe el inventario con ese ID",((HashMap) response.getBody()).get("message"));
    }

}


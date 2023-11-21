package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Producto;
import com.challenge.modelo_de_datos.model.TipoProducto;
import com.challenge.modelo_de_datos.model.Vendedor;
import com.challenge.modelo_de_datos.repository.ProductoRepository;
import com.challenge.modelo_de_datos.repository.TipoProductoRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
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

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private TipoProductoRepository tipoProductoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Vendedor vendedor;

    private TipoProducto tipoProducto;

    private Producto producto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        producto = new Producto();
    }

    @Test
    void getProducto() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));
        assertNotNull(productoService.getProductos());
    }

    @Test
    public void testNewProducto_Success() {
        producto.setNombre("prueba");
        producto.setDescripcion("prueba");
        producto.setPrecio(10);
        producto.setDetalle("1");
        producto.setVendedor(new Vendedor());
        producto.setTipoProducto(new TipoProducto());
        when(vendedorRepository.existsById(producto.getVendedor().getIdVendedor())).thenReturn(true);
        when(tipoProductoRepository.existsById(producto.getTipoProducto().getIdTipoProducto())).thenReturn(true);
        when(productoRepository.save(producto)).thenReturn(producto);
        ResponseEntity<Object> response = productoService.newProducto(producto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void testNewProducto_ErrorID() {
        producto.setIdProducto(1);
        ResponseEntity<Object> response = productoService.newProducto(producto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No enviar ID, este se genera automáticamente", ((HashMap) response.getBody()).get("message"));
        verify(productoRepository, never()).save(producto);
    }

    @Test
    public void testNewProducto_BlankFields() {
        producto.setNombre(null);
        producto.setDescripcion(null);
        producto.setDetalle(null);
        producto.setVendedor(null);
        producto.setTipoProducto(new TipoProducto());
        when(tipoProductoRepository.existsById(producto.getTipoProducto().getIdTipoProducto())).thenReturn(true);
        when(productoRepository.save(producto)).thenReturn(producto);
        ResponseEntity<Object> response = productoService.updateProducto(1,producto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("ingresa los campos de la tabla", ((HashMap) response.getBody()).get("message"));
        verify(productoRepository, never()).save(producto);
    }

    @Test
    public void testNewProducto_Validate() {
        producto.setNombre(null);
        producto.setDescripcion("prueba");
        producto.setPrecio(10);
        producto.setDetalle("hfj");
        producto.setVendedor(null);
        producto.setTipoProducto(new TipoProducto());
        when(tipoProductoRepository.existsById(producto.getTipoProducto().getIdTipoProducto())).thenReturn(true);
        when(productoRepository.save(producto)).thenReturn(producto);
        ResponseEntity<Object> response = productoService.newProducto(producto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El nombre del producto es obligatorio.", ((HashMap) response.getBody()).get("message"));
        verify(productoRepository, never()).save(producto);
    }

    @Test
    public void testNewProducto_Price() {
        producto.setNombre("prueba");
        producto.setDescripcion("5");
        producto.setPrecio(10);
        producto.setDetalle("1");
        producto.setVendedor(new Vendedor());
        producto.setTipoProducto(new TipoProducto());
        when(vendedorRepository.existsById(producto.getVendedor().getIdVendedor())).thenReturn(true);
        when(tipoProductoRepository.existsById(producto.getTipoProducto().getIdTipoProducto())).thenReturn(true);
        when(productoRepository.save(producto)).thenReturn(producto);
        ResponseEntity<Object> response = productoService.newProducto(producto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caractares no deden ser numeros", ((HashMap) response.getBody()).get("message"));
        verify(productoRepository, never()).save(producto);
    }

    @Test
    public void testNewProducto_Name() {
        producto.setNombre("5");
        producto.setDescripcion("prueba");
        producto.setPrecio(10);
        producto.setDetalle("1");
        producto.setVendedor(new Vendedor());
        producto.setTipoProducto(new TipoProducto());
        when(vendedorRepository.existsById(producto.getVendedor().getIdVendedor())).thenReturn(true);
        when(tipoProductoRepository.existsById(producto.getTipoProducto().getIdTipoProducto())).thenReturn(true);
        when(productoRepository.save(producto)).thenReturn(producto);
        ResponseEntity<Object> response = productoService.newProducto(producto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));
        verify(productoRepository, never()).save(producto);
    }


    @Test
    public void testUpdateProducto_Success() {
        producto.setNombre("prueba");
        producto.setDescripcion("prueba");
        producto.setPrecio(1);
        producto.setDetalle("dfas");
        producto.setVendedor(new Vendedor());
        producto.setTipoProducto(new TipoProducto());
        when(vendedorRepository.existsById(producto.getVendedor().getIdVendedor())).thenReturn(true);
        when(tipoProductoRepository.existsById(producto.getTipoProducto().getIdTipoProducto())).thenReturn(true);
        when(productoRepository.save(producto)).thenReturn(producto);
        ResponseEntity<Object> response = productoService.updateProducto(1, producto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testUpdateProducto_NullSeller_TypeProduct() {
        producto.setIdProducto(0);
        producto.setNombre("prueba");
        producto.setDescripcion("prueba");
        producto.setPrecio(10);
        producto.setDetalle("1");
        producto.setVendedor(null);
        producto.setTipoProducto(null);
        when(productoRepository.save(producto)).thenReturn(producto);
        ResponseEntity<Object> response = productoService.updateProducto(1, producto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("ingresa los campos de la tabla", ((HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testUpdateProducto_ErrorID() {
        producto.setNombre("prueba");
        producto.setDescripcion("prueba");
        producto.setPrecio(10);
        producto.setDetalle("1");
        producto.setVendedor(new Vendedor());
        producto.setTipoProducto(new TipoProducto());
        when(vendedorRepository.existsById(producto.getVendedor().getIdVendedor())).thenReturn(true);
        when(tipoProductoRepository.existsById(producto.getTipoProducto().getIdTipoProducto())).thenReturn(true);
        when(productoRepository.save(producto)).thenReturn(producto);
        ResponseEntity<Object> response = productoService.updateProducto(1, producto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id del producto proporcionado es erroneo", ((HashMap) response.getBody()).get("message"));
    }




}

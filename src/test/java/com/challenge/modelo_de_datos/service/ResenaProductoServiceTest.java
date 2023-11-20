package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Producto;
import com.challenge.modelo_de_datos.model.ResenaProducto;
import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.repository.ProductoRepository;
import com.challenge.modelo_de_datos.repository.ResenaProductoRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
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

class ResenaProductoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private ResenaProductoRepository resenaProductoRepository;

    @InjectMocks
    private ResenaProductoService resenaProductoService;

    private Usuario usuario;
    private Producto producto;

    private ResenaProducto resenaProducto;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        resenaProducto = new ResenaProducto();
    }

    @Test
    void getResenasProducto() {
        when(resenaProductoRepository.findAll()).thenReturn(Arrays.asList(resenaProducto));
        assertNotNull(resenaProductoService.getResenasProducto());

    }

    @Test
    public void testNewResenaProducto_Success() {
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        when(resenaProductoRepository.save(resenaProducto)).thenReturn(resenaProducto);
        ResponseEntity<Object> response = resenaProductoService.newResenaProducto(resenaProducto);
        assertEquals("SE GUARDO RESEÑA CON EXITO", ((java.util.HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testNewResenaProducto_BadRequest_InvalidId() {
        resenaProducto.setIdResenaProducto(1);
        ResponseEntity<Object> response = resenaProductoService.newResenaProducto(resenaProducto);
        assertEquals("No enviar ID, este se genera automáticamente", ((java.util.HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testNewResenaProducto_BadRequest_InvalidIdUsuario() {
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(false);
        ResponseEntity<Object> response = resenaProductoService.newResenaProducto(resenaProducto);
        assertEquals("El usuario no existe, ingrese un ID valido", ((java.util.HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testNewResenaProducto_BadRequest_InvalidIdProducto() {
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(false);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response = resenaProductoService.newResenaProducto(resenaProducto);
        assertEquals("El producto no existe, ingrese un ID valido", ((java.util.HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testNewResenaProducto_BadRequest_NullFields(){
        resenaProducto.setDescripcion(null);
        resenaProducto.setProducto(null);
        resenaProducto.setUsuario(null);
        ResponseEntity<Object> response = resenaProductoService.newResenaProducto(resenaProducto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testNewResenaProducto_Conflict_Blank() {
        resenaProducto.setDescripcion("");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response = resenaProductoService.newResenaProducto(resenaProducto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El nombre del producto es obligatorio.", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testNewResenaProducto_Conflict_Numeric() {
        resenaProducto.setDescripcion("123");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response = resenaProductoService.newResenaProducto(resenaProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testNewResenaProducto_Conflict_Negative() {
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        resenaProducto.setCalificacionProducto(-1);
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response = resenaProductoService.newResenaProducto(resenaProducto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("LA CALIFICACION DEBE SER NUMERICA POSITIVA", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testUpdateResenaProducto_Success() {
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        resenaProducto.setCalificacionProducto(1);
        resenaProducto.setIdResenaProducto(1);
        when(resenaProductoRepository.existsById(resenaProducto.getIdResenaProducto())).thenReturn(true);
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        when(resenaProductoRepository.save(resenaProducto)).thenReturn(resenaProducto);
        ResponseEntity<Object> response = resenaProductoService.updateResenaProducto(1,resenaProducto);
        assertEquals("SE GUARDO RESEÑA CON EXITO", ((java.util.HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testUpdateResenaProducto_BadRequest_InvalidId() {
        resenaProducto.setIdResenaProducto(1);
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        resenaProducto.setCalificacionProducto(1);
        ResponseEntity<Object> response = resenaProductoService.updateResenaProducto(1,resenaProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("La reseña no existe", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testUpdateResenaProducto_BadRequest_InvalidIdUsuario() {
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        resenaProducto.setCalificacionProducto(1);
        resenaProducto.setIdResenaProducto(1);
        when(resenaProductoRepository.existsById(resenaProducto.getIdResenaProducto())).thenReturn(true);
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(false);
        ResponseEntity<Object> response = resenaProductoService.updateResenaProducto(1,resenaProducto);
        assertEquals("El usuario no existe, ingrese un ID valido", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testUpdateResenaProducto_BadRequest_InvalidIdProducto() {
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        resenaProducto.setCalificacionProducto(1);
        resenaProducto.setIdResenaProducto(1);
        when(resenaProductoRepository.existsById(resenaProducto.getIdResenaProducto())).thenReturn(true);
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(false);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response = resenaProductoService.updateResenaProducto(1,resenaProducto);
        assertEquals("El producto no existe, ingrese un ID valido", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testUpdateResenaProducto_BadRequest_NullFields(){
        resenaProducto.setDescripcion(null);
        resenaProducto.setProducto(null);
        resenaProducto.setUsuario(null);
        resenaProducto.setCalificacionProducto(1);
        resenaProducto.setIdResenaProducto(1);
        ResponseEntity<Object> response = resenaProductoService.updateResenaProducto(1,resenaProducto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testUpdateResenaProducto_Conflict_Blank() {
        resenaProducto.setDescripcion("");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        resenaProducto.setCalificacionProducto(1);
        resenaProducto.setIdResenaProducto(1);
        when(resenaProductoRepository.existsById(resenaProducto.getIdResenaProducto())).thenReturn(true);
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response = resenaProductoService.updateResenaProducto(1,resenaProducto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El nombre del producto es obligatorio.", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testUpdateResenaProducto_Conflict_Numeric() {
        resenaProducto.setDescripcion("123");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        resenaProducto.setCalificacionProducto(1);
        resenaProducto.setIdResenaProducto(1);
        when(resenaProductoRepository.existsById(resenaProducto.getIdResenaProducto())).thenReturn(true);
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response = resenaProductoService.updateResenaProducto(1,resenaProducto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void testUpdateResenaProducto_Conflict_Negative() {
        resenaProducto.setDescripcion("prueba");
        resenaProducto.setProducto(new Producto());
        resenaProducto.setUsuario(new Usuario());
        resenaProducto.setCalificacionProducto(-1);
        resenaProducto.setIdResenaProducto(1);
        when(resenaProductoRepository.existsById(resenaProducto.getIdResenaProducto())).thenReturn(true);
        when(productoRepository.existsById(resenaProducto.getProducto().getIdProducto())).thenReturn(true);
        when(usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response = resenaProductoService.updateResenaProducto(1,resenaProducto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("LA CALIFICACION DEBE SER NUMERICA POSITIVA", ((java.util.HashMap) response.getBody()).get("message"));
        verify(resenaProductoRepository, never()).save(resenaProducto);
    }

    @Test
    public void deleteResenaProducto_error(){
        resenaProducto.setIdResenaProducto(1);
        when(resenaProductoRepository.existsById(resenaProducto.getIdResenaProducto())).thenReturn(false);
        ResponseEntity<Object> response =resenaProductoService.deleteResenaProducto(1);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No hay resenaProducto con ese id",((java.util.HashMap) response.getBody()).get("message"));
    }

    @Test
    public void deleteResenaProducto() {
        resenaProducto.setIdResenaProducto(1);
        when(resenaProductoRepository.existsById(resenaProducto.getIdResenaProducto())).thenReturn(true);
        ResponseEntity<Object> response =resenaProductoService.deleteResenaProducto(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("resenaProducto eliminada",((java.util.HashMap) response.getBody()).get("message"));
    }

}
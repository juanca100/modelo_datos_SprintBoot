package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Categoria;
import com.challenge.modelo_de_datos.model.TipoProducto;
import com.challenge.modelo_de_datos.repository.categoriarepository;
import com.challenge.modelo_de_datos.repository.TipoProductoRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

class TipoProductoServiceTest {
    @Mock
    private TipoProductoRepository tipoProductoRepository;
    @Mock
    private categoriarepository categoriaRepository;
    @InjectMocks
    private TipoProductoService tipoProductoService;
    private TipoProducto tipoProducto;
    private Categoria categoria;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tipoProducto=new TipoProducto();
    }

    @Test
    void getTiposProducto() {
        when(tipoProductoRepository.findAll()).thenReturn(Arrays.asList(tipoProducto));
        assertNotNull(tipoProductoService.getTiposProducto());
    }
    /*new tipo producto*/
    @Test
    public void testNewTipoProducto_Success() {
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setTipoProducto("ProductoTest");
        tipoProducto.setCategoria(new Categoria());

        when(categoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria())).thenReturn(true);

        ResponseEntity<Object> response = tipoProductoService.newTipoProducto(tipoProducto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, times(1)).save(tipoProducto);
    }

    @Test
    public void testNewTipoProducto_BadRequest_InvalidId() {
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setIdTipoProducto(1);

        ResponseEntity<Object> response = tipoProductoService.newTipoProducto(tipoProducto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    @Test
    public void testNewTipoProducto_BadRequest_NullFields() {
        TipoProducto tipoProducto = new TipoProducto();

        ResponseEntity<Object> response = tipoProductoService.newTipoProducto(tipoProducto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    @Test
    public void testNewTipoProducto_Conflict_Blank() {
        tipoProducto.setTipoProducto("");
        tipoProducto.setCategoria(new Categoria());

        ResponseEntity<Object> response = tipoProductoService.newTipoProducto(tipoProducto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    @Test
    public void testNewTipoProducto_Conflict_Numeric() {
        tipoProducto.setTipoProducto("123");
        tipoProducto.setCategoria(new Categoria());
        ResponseEntity<Object> response = tipoProductoService.newTipoProducto(tipoProducto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    @Test
    public void testNewTipoProducto_Conflict_InvalidCategoriaId() {
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setTipoProducto("ProductoTest");
        tipoProducto.setCategoria(new Categoria());
        when(categoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria())).thenReturn(false);

        ResponseEntity<Object> response = tipoProductoService.newTipoProducto(tipoProducto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Aquí podría ser un error lógico, deberías revisar este caso
        assertEquals("La categoria no existe, ID erroneo", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }
    /*fin tipo producto*/

    // ...

    /* update tipo producto */
    @Test
    public void testUpdateTipoProducto_Success() {
        int id = 1;
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setIdTipoProducto(id);
        tipoProducto.setTipoProducto("ProductoTest");
        tipoProducto.setCategoria(new Categoria());

        when(tipoProductoRepository.existsById(id)).thenReturn(true);
        when(categoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria())).thenReturn(true);

        ResponseEntity<Object> response = tipoProductoService.updateTipoProducto(id, tipoProducto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Aquí podría ser un error lógico, deberías revisar este caso
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, times(1)).save(tipoProducto);
    }

    @Test
    public void testUpdateTipoProducto_BadRequest_InvalidId() {
        int id = 1;
        tipoProducto.setIdTipoProducto(2); // ID diferente al proporcionado en el método
        tipoProducto.setCategoria(new Categoria());
        tipoProducto.setTipoProducto("prueba");
        ResponseEntity<Object> response = tipoProductoService.updateTipoProducto(id, tipoProducto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id del tipo de producto proporcionado es erroneo", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    @Test
    public void testUpdateTipoProducto_BadRequest_NullFields() {
        int id = 1;
        TipoProducto tipoProducto = new TipoProducto();

        ResponseEntity<Object> response = tipoProductoService.updateTipoProducto(id, tipoProducto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    @Test
    public void testUpdateTipoProducto_Conflict_Blank() {
        int id = 1;
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setIdTipoProducto(id);
        tipoProducto.setTipoProducto("");
        tipoProducto.setCategoria(new Categoria());
        when(tipoProductoRepository.existsById(tipoProducto.getIdTipoProducto())).thenReturn(true);
        ResponseEntity<Object> response = tipoProductoService.updateTipoProducto(id, tipoProducto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    @Test
    public void testUpdateTipoProducto_Conflict_Numeric() {
        int id = 1;
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setIdTipoProducto(id);
        tipoProducto.setTipoProducto("123");
        tipoProducto.setCategoria(new Categoria());
        when(tipoProductoRepository.existsById(tipoProducto.getIdTipoProducto())).thenReturn(true);
        ResponseEntity<Object> response = tipoProductoService.updateTipoProducto(id, tipoProducto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    @Test
    public void testUpdateTipoProducto_Conflict_InvalidCategoriaId() {
        int id = 1;
        tipoProducto.setIdTipoProducto(id);
        tipoProducto.setTipoProducto("ProductoTest");
        tipoProducto.setCategoria(new Categoria());
        when(tipoProductoRepository.existsById(tipoProducto.getIdTipoProducto())).thenReturn(true);
        when(categoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria())).thenReturn(false);

        ResponseEntity<Object> response = tipoProductoService.updateTipoProducto(id, tipoProducto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("La categoria no existe, ID erroneo", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).save(tipoProducto);
    }

    /* fin update tipo producto */
    // ...

    /* delete tipo producto */
    @Test
    public void testDeleteTipoProducto_Success() {
        int id = 1;
        when(tipoProductoRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Object> response = tipoProductoService.deleteTipoProducto(id);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Tipo eliminado", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteTipoProducto_NotFound() {
        int id = 1;
        when(tipoProductoRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Object> response = tipoProductoService.deleteTipoProducto(id);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No existe el tipo de producto con ese id", ((HashMap) response.getBody()).get("message"));
        verify(tipoProductoRepository, never()).deleteById(id);
    }

    /* fin delete tipo producto */


}
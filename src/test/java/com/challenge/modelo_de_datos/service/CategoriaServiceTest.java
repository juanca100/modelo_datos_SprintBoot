package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Categoria;
import com.challenge.modelo_de_datos.repository.CategoriaRepository;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoria = new Categoria();
        categoria.setCategoria("prueba");
    }

    @Test
    void getCategorias() {
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria));
        assertNotNull(categoriaService.getCategorias());
    }


    @Test
    void newCategoria() {
        //TEST CUANDO EL ID NO ES 0
        categoria.setIdCategoria(1);
        ResponseEntity<Object> responseWithNonZeroId = categoriaService.newCategoria(categoria);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNonZeroId.getStatusCode());
        assertTrue(responseWithNonZeroId.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS SON NULOS
        categoria.setIdCategoria(0);
        categoria.setCategoria(null);
        ResponseEntity<Object> responseWithNullCategoria = categoriaService.newCategoria(categoria);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullCategoria.getStatusCode());
        assertTrue(responseWithNullCategoria.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS ESTAN EN BLANCOS
        categoria.setCategoria("");
        ResponseEntity<Object> responseWithBlankCategoria = categoriaService.newCategoria(categoria);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankCategoria.getStatusCode());
        assertTrue(responseWithBlankCategoria.getBody() instanceof HashMap);

        // TEST DE COMPROBACION DE NUMEROS
        categoria.setCategoria("123");
        ResponseEntity<Object> responseWithNumericCategoria = categoriaService.newCategoria(categoria);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericCategoria.getStatusCode());
        assertTrue(responseWithNumericCategoria.getBody() instanceof HashMap);

        // TEST DE VALIDACION
        categoria.setCategoria("ValidCategoria");
        ResponseEntity<Object> responseWithValidCategoria = categoriaService.newCategoria(categoria);
        assertEquals(HttpStatus.CREATED, responseWithValidCategoria.getStatusCode());
        assertTrue(responseWithValidCategoria.getBody() instanceof HashMap);

    }

    @Test
    void updateCategoria() {
        // Configuración del escenario
        int idCategoriaExistente = 1;
        int idCategoriaNoExistente = 2; // ID que no existe

        when(categoriaRepository.existsById(idCategoriaExistente)).thenReturn(true);

        // Caso cuando el campo 'categoria' es nulo
        categoria.setIdCategoria(idCategoriaExistente);
        categoria.setCategoria(null);
        ResponseEntity<Object> responseWithNullCategoria = categoriaService.updateCategoria(idCategoriaExistente, categoria);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullCategoria.getStatusCode());
        assertTrue(responseWithNullCategoria.getBody() instanceof HashMap);

        // Caso cuando el campo 'categoria' está en blanco
        categoria.setCategoria("");
        ResponseEntity<Object> responseWithBlankCategoria = categoriaService.updateCategoria(idCategoriaExistente, categoria);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankCategoria.getStatusCode());
        assertTrue(responseWithBlankCategoria.getBody() instanceof HashMap);

        // Caso cuando el campo 'categoria' contiene números
        categoria.setCategoria("123");
        ResponseEntity<Object> responseWithNumericCategoria = categoriaService.updateCategoria(idCategoriaExistente, categoria);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericCategoria.getStatusCode());
        assertTrue(responseWithNumericCategoria.getBody() instanceof HashMap);

        // Caso cuando la actualización es exitosa
        categoria.setCategoria("ValidCategoria");
        ResponseEntity<Object> responseWithValidCategoria = categoriaService.updateCategoria(idCategoriaExistente, categoria);
        assertEquals(HttpStatus.CREATED, responseWithValidCategoria.getStatusCode());
        assertTrue(responseWithValidCategoria.getBody() instanceof HashMap);

        // Caso cuando el ID no existe
        ResponseEntity<Object> responseWithNonExistingId = categoriaService.updateCategoria(idCategoriaNoExistente, categoria);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);

        // Verificar que el método save se haya llamado una vez
        verify(categoriaRepository, times(1)).save(categoria);


    }

    @Test
    void deleteCategoria() {
        // Configuración del repositorio mock
        int idToDelete = 1;
        when(categoriaRepository.existsById(idToDelete)).thenReturn(true);

        // Prueba cuando el ID existe
        ResponseEntity<Object> responseWithExistingId = categoriaService.deleteCategoria(idToDelete);
        assertEquals(HttpStatus.ACCEPTED, responseWithExistingId.getStatusCode());
        assertTrue(responseWithExistingId.getBody() instanceof HashMap);

        // Verificar que se llamó a deleteById con el ID proporcionado
        verify(categoriaRepository, times(1)).deleteById(idToDelete);

        // Prueba cuando el ID no existe
        int nonExistingId = 2;
        when(categoriaRepository.existsById(nonExistingId)).thenReturn(false);

        ResponseEntity<Object> responseWithNonExistingId = categoriaService.deleteCategoria(nonExistingId);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);
    }
}
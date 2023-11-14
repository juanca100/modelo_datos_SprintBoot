package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Pais;
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

class PaisServiceTest {
    @Mock
    private PaisRepository paisRepository;

    @InjectMocks
    private PaisService paisService;

    private Pais pais;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pais = new Pais();
        pais.setPais("prueba");
    }

    @Test
    void getPaises() {
        when(paisRepository.findAll()).thenReturn(Arrays.asList(pais));
        assertNotNull(paisService.getPaises());
    }


    @Test
    void newPais() {
        //TEST CUANDO EL ID NO ES 0
        pais.setIdPais(1);
        ResponseEntity<Object> responseWithNonZeroId = paisService.newPais(pais);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNonZeroId.getStatusCode());
        assertTrue(responseWithNonZeroId.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS SON NULOS
        pais.setIdPais(0);
        pais.setPais(null);
        ResponseEntity<Object> responseWithNullPais = paisService.newPais(pais);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullPais.getStatusCode());
        assertTrue(responseWithNullPais.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS ESTAN EN BLANCOS
        pais.setPais("");
        ResponseEntity<Object> responseWithBlankPais = paisService.newPais(pais);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankPais.getStatusCode());
        assertTrue(responseWithBlankPais.getBody() instanceof HashMap);

        // TEST DE COMPROBACION DE NUMEROS
        pais.setPais("123");
        ResponseEntity<Object> responseWithNumericPais = paisService.newPais(pais);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericPais.getStatusCode());
        assertTrue(responseWithNumericPais.getBody() instanceof HashMap);

        // TEST DE VALIDACION
        pais.setPais("ValidPais");
        ResponseEntity<Object> responseWithPais = paisService.newPais(pais);
        assertEquals(HttpStatus.CREATED, responseWithPais.getStatusCode());
        assertTrue(responseWithPais.getBody() instanceof HashMap);

    }

    @Test
    void updatePais() {
        // Configuración del escenario
        int idPaisExistente= 1;
        int idPaisNoExistente = 2; // ID que no existe

        when(paisRepository.existsById(idPaisExistente)).thenReturn(true);

        // Caso cuando el campo 'pais' es nulo
        pais.setIdPais(idPaisExistente);
        pais.setPais(null);
        ResponseEntity<Object> responseWithNullPais = paisService.updatePais(idPaisExistente, pais);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullPais.getStatusCode());
        assertTrue(responseWithNullPais.getBody() instanceof HashMap);

        // Caso cuando el campo 'pais' está en blanco
        pais.setPais("");
        ResponseEntity<Object> responseWithBlankPais = paisService.updatePais(idPaisExistente, pais);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankPais.getStatusCode());
        assertTrue(responseWithBlankPais.getBody() instanceof HashMap);

        // Caso cuando el campo 'pais' contiene números
        pais.setPais("123");
        ResponseEntity<Object> responseWithNumericPais = paisService.updatePais(idPaisExistente, pais);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericPais.getStatusCode());
        assertTrue(responseWithNumericPais.getBody() instanceof HashMap);

        // Caso cuando la actualización es exitosa
        pais.setPais("ValidPais");
        ResponseEntity<Object> responseWithValidPais = paisService.updatePais(idPaisExistente, pais);
        assertEquals(HttpStatus.CREATED, responseWithValidPais.getStatusCode());
        assertTrue(responseWithValidPais.getBody() instanceof HashMap);

        // Caso cuando el ID no existe
        ResponseEntity<Object> responseWithNonExistingId = paisService.updatePais(idPaisNoExistente, pais);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);

        // Verificar que el método save se haya llamado una vez
        verify(paisRepository, times(1)).save(pais);


    }

    @Test
    void deletePais() {
        // Configuración del repositorio mock
        int idToDelete = 1;
        when(paisRepository.existsById(idToDelete)).thenReturn(true);

        // Prueba cuando el ID existe
        ResponseEntity<Object> responseWithExistingId = paisService.deletePais(idToDelete);
        assertEquals(HttpStatus.ACCEPTED, responseWithExistingId.getStatusCode());
        assertTrue(responseWithExistingId.getBody() instanceof HashMap);

        // Verificar que se llamó a deleteById con el ID proporcionado
        verify(paisRepository, times(1)).deleteById(idToDelete);

        // Prueba cuando el ID no existe
        int nonExistingId = 2;
        when(paisRepository.existsById(nonExistingId)).thenReturn(false);

        ResponseEntity<Object> responseWithNonExistingId = paisService.deletePais(nonExistingId);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);
    }
}
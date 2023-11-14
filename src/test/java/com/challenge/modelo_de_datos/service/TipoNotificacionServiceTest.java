package com.challenge.modelo_de_datos.service;


import com.challenge.modelo_de_datos.model.TipoNotificacion;
import com.challenge.modelo_de_datos.repository.TipoNotificacionRepository;
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

class TipoNotificacionServiceTest {
    @Mock
    private TipoNotificacionRepository tipoNotificacionRepository;

    @InjectMocks
    private TipoNotificacionService tipoNotificacionService;

    private TipoNotificacion tipoNotificacion;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tipoNotificacion = new TipoNotificacion();
        tipoNotificacion.setTipoNotificacion("prueba");
    }

    @Test
    void getTipoNotificaciones() {
        when(tipoNotificacionRepository.findAll()).thenReturn(Arrays.asList(tipoNotificacion));
        assertNotNull(tipoNotificacionService.getTiposNotificaciones());
    }


    @Test
    void newTipoNotificacion() {
        //TEST CUANDO EL ID NO ES 0
        tipoNotificacion.setIdTipoNotificacion(1);
        ResponseEntity<Object> responseWithNonZeroId = tipoNotificacionService.newTipoNotificacion(tipoNotificacion);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNonZeroId.getStatusCode());
        assertTrue(responseWithNonZeroId.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS SON NULOS
        tipoNotificacion.setIdTipoNotificacion(0);
        tipoNotificacion.setTipoNotificacion(null);
        ResponseEntity<Object> responseWithNullTipoNotificacion = tipoNotificacionService.newTipoNotificacion(tipoNotificacion);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullTipoNotificacion.getStatusCode());
        assertTrue(responseWithNullTipoNotificacion.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS ESTAN EN BLANCOS
        tipoNotificacion.setTipoNotificacion("");
        ResponseEntity<Object> responseWithBlankTipoNotificacion = tipoNotificacionService.newTipoNotificacion(tipoNotificacion);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankTipoNotificacion.getStatusCode());
        assertTrue(responseWithBlankTipoNotificacion.getBody() instanceof HashMap);

        // TEST DE COMPROBACION DE NUMEROS
        tipoNotificacion.setTipoNotificacion("123");
        ResponseEntity<Object> responseWithNumericTipoNotificacion = tipoNotificacionService.newTipoNotificacion(tipoNotificacion);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericTipoNotificacion.getStatusCode());
        assertTrue(responseWithNumericTipoNotificacion.getBody() instanceof HashMap);

        // TEST DE VALIDACION
        tipoNotificacion.setTipoNotificacion("ValidTipoNotificacion");
        ResponseEntity<Object> responseWithValidTipoNotificacion = tipoNotificacionService.newTipoNotificacion(tipoNotificacion);
        assertEquals(HttpStatus.CREATED, responseWithValidTipoNotificacion.getStatusCode());
        assertTrue(responseWithValidTipoNotificacion.getBody() instanceof HashMap);

    }

    @Test
    void updateTipoNotificacion() {
        // Configuración del escenario
        int idTipoNotificacionExistente = 1;
        int idTipoNotificacionNoExistente = 2; // ID que no existe

        when(tipoNotificacionRepository.existsById(idTipoNotificacionExistente)).thenReturn(true);

        // Caso cuando el campo 'tipoNotificacion' es nulo
        tipoNotificacion.setIdTipoNotificacion(idTipoNotificacionExistente);
        tipoNotificacion.setTipoNotificacion(null);
        ResponseEntity<Object> responseWithNullTipoNotificacion = tipoNotificacionService.updateTipoNotificacion(idTipoNotificacionExistente, tipoNotificacion);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullTipoNotificacion.getStatusCode());
        assertTrue(responseWithNullTipoNotificacion.getBody() instanceof HashMap);

        // Caso cuando el campo 'tipoNotificacion' está en blanco
        tipoNotificacion.setTipoNotificacion("");
        ResponseEntity<Object> responseWithBlankTipoNotificacion = tipoNotificacionService.updateTipoNotificacion(idTipoNotificacionExistente, tipoNotificacion);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankTipoNotificacion.getStatusCode());
        assertTrue(responseWithBlankTipoNotificacion.getBody() instanceof HashMap);

        // Caso cuando el campo 'tipoNotificacion' contiene números
        tipoNotificacion.setTipoNotificacion("123");
        ResponseEntity<Object> responseWithNumericTipoNotificacion = tipoNotificacionService.updateTipoNotificacion(idTipoNotificacionExistente, tipoNotificacion);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericTipoNotificacion.getStatusCode());
        assertTrue(responseWithNumericTipoNotificacion.getBody() instanceof HashMap);

        // Caso cuando la actualización es exitosa
        tipoNotificacion.setTipoNotificacion("ValidTipoNotificacion");
        ResponseEntity<Object> responseWithValidTipoNotificacion = tipoNotificacionService.updateTipoNotificacion(idTipoNotificacionExistente, tipoNotificacion);
        assertEquals(HttpStatus.CREATED, responseWithValidTipoNotificacion.getStatusCode());
        assertTrue(responseWithValidTipoNotificacion.getBody() instanceof HashMap);

        // Caso cuando el ID no existe
        ResponseEntity<Object> responseWithNonExistingId = tipoNotificacionService.updateTipoNotificacion(idTipoNotificacionNoExistente, tipoNotificacion);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);

        // Verificar que el método save se haya llamado una vez
        verify(tipoNotificacionRepository, times(1)).save(tipoNotificacion);


    }

    @Test
    void deleteTipoNotificacion() {
        // Configuración del repositorio mock
        int idToDelete = 1;
        when(tipoNotificacionRepository.existsById(idToDelete)).thenReturn(true);

        // Prueba cuando el ID existe
        ResponseEntity<Object> responseWithExistingId = tipoNotificacionService.deleteTipoNotificacion(idToDelete);
        assertEquals(HttpStatus.ACCEPTED, responseWithExistingId.getStatusCode());
        assertTrue(responseWithExistingId.getBody() instanceof HashMap);

        // Verificar que se llamó a deleteById con el ID proporcionado
        verify(tipoNotificacionRepository, times(1)).deleteById(idToDelete);

        // Prueba cuando el ID no existe
        int nonExistingId = 2;
        when(tipoNotificacionRepository.existsById(nonExistingId)).thenReturn(false);

        ResponseEntity<Object> responseWithNonExistingId = tipoNotificacionService.deleteTipoNotificacion(nonExistingId);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);
    }
}
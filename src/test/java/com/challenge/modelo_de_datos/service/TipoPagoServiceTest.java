package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.model.TipoPago;
import com.challenge.modelo_de_datos.repository.PaisRepository;
import com.challenge.modelo_de_datos.repository.TipoPagoRepository;
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

class TipoPagoServiceTest {
    @Mock
    private TipoPagoRepository tipoPagoRepository;

    @InjectMocks
    private TipoPagoService tipoPagoService;

    private TipoPago tipoPago;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tipoPago = new TipoPago();
        tipoPago.setTipoPago("prueba");
    }

    @Test
    void getTiposPago() {
        when(tipoPagoRepository.findAll()).thenReturn(Arrays.asList(tipoPago));
        assertNotNull(tipoPagoService.getTiposPago());
    }


    @Test
    void newTipoPago() {
        //TEST CUANDO EL ID NO ES 0
        tipoPago.setIdTipoPago(1);
        ResponseEntity<Object> responseWithNonZeroId = tipoPagoService.newTipoPago(tipoPago);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNonZeroId.getStatusCode());
        assertTrue(responseWithNonZeroId.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS SON NULOS
        tipoPago.setIdTipoPago(0);
        tipoPago.setTipoPago(null);
        ResponseEntity<Object> responseWithNullTipoPago = tipoPagoService.newTipoPago(tipoPago);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullTipoPago.getStatusCode());
        assertTrue(responseWithNullTipoPago.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS ESTAN EN BLANCOS
        tipoPago.setTipoPago("");
        ResponseEntity<Object> responseWithBlankTipoPago = tipoPagoService.newTipoPago(tipoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankTipoPago.getStatusCode());
        assertTrue(responseWithBlankTipoPago.getBody() instanceof HashMap);

        // TEST DE COMPROBACION DE NUMEROS
        tipoPago.setTipoPago("123");
        ResponseEntity<Object> responseWithNumericTipoPago = tipoPagoService.newTipoPago(tipoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericTipoPago.getStatusCode());
        assertTrue(responseWithNumericTipoPago.getBody() instanceof HashMap);

        // TEST DE VALIDACION
        tipoPago.setTipoPago("ValidPais");
        ResponseEntity<Object> responseWithTipoPago = tipoPagoService.newTipoPago(tipoPago);
        assertEquals(HttpStatus.CREATED, responseWithTipoPago.getStatusCode());
        assertTrue(responseWithTipoPago.getBody() instanceof HashMap);

    }

    @Test
    void updatePais() {
        // Configuración del escenario
        int idTipoPagoExistente= 1;
        int idTipoPagoNoExistente = 50; // ID que no existe

        when(tipoPagoRepository.existsById(idTipoPagoExistente)).thenReturn(true);

        // Caso cuando el campo 'pais' es nulo
        tipoPago.setIdTipoPago(idTipoPagoExistente);
        tipoPago.setTipoPago(null);
        ResponseEntity<Object> responseWithNullTipoPago = tipoPagoService.updateTipoPago(idTipoPagoExistente, tipoPago);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullTipoPago.getStatusCode());
        assertTrue(responseWithNullTipoPago.getBody() instanceof HashMap);

        // Caso cuando el campo 'pais' está en blanco
        tipoPago.setTipoPago("");
        ResponseEntity<Object> responseWithBlankTipoPago = tipoPagoService.updateTipoPago(idTipoPagoExistente, tipoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankTipoPago.getStatusCode());
        assertTrue(responseWithBlankTipoPago.getBody() instanceof HashMap);

        // Caso cuando el campo 'pais' contiene números
        tipoPago.setTipoPago("123");
        ResponseEntity<Object> responseWithNumericTipoPago = tipoPagoService.updateTipoPago(idTipoPagoExistente, tipoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericTipoPago.getStatusCode());
        assertTrue(responseWithNumericTipoPago.getBody() instanceof HashMap);

        // Caso cuando la actualización es exitosa
        tipoPago.setTipoPago("ValidPais");
        ResponseEntity<Object> responseWithValidTipoPago = tipoPagoService.updateTipoPago(idTipoPagoExistente, tipoPago);
        assertEquals(HttpStatus.CREATED, responseWithValidTipoPago.getStatusCode());
        assertTrue(responseWithValidTipoPago.getBody() instanceof HashMap);

        // Caso cuando el ID no existe
        ResponseEntity<Object> responseWithNonExistingId = tipoPagoService.updateTipoPago(idTipoPagoNoExistente, tipoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);

        // Verificar que el método save se haya llamado una vez
        verify(tipoPagoRepository, times(1)).save(tipoPago);
    }

    @Test
    void deleteTipoPago() {
        // Configuración del repositorio mock
        int idToDelete = 1;
        when(tipoPagoRepository.existsById(idToDelete)).thenReturn(true);

        // Prueba cuando el ID existe
        ResponseEntity<Object> responseWithExistingId = tipoPagoService.deleteTipoPago(idToDelete);
        assertEquals(HttpStatus.ACCEPTED, responseWithExistingId.getStatusCode());
        assertTrue(responseWithExistingId.getBody() instanceof HashMap);

        // Verificar que se llamó a deleteById con el ID proporcionado
        verify(tipoPagoRepository, times(1)).deleteById(idToDelete);

        // Prueba cuando el ID no existe
        int nonExistingId = 2;
        when(tipoPagoRepository.existsById(nonExistingId)).thenReturn(false);

        ResponseEntity<Object> responseWithNonExistingId = tipoPagoService.deleteTipoPago(nonExistingId);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);
    }
}
package com.challenge.modelo_de_datos.service;
import com.challenge.modelo_de_datos.model.EstadoPago;
import com.challenge.modelo_de_datos.repository.EstadoPagoRepository;
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

class EstadoPagoServiceTest {
    @Mock
    private EstadoPagoRepository estadoPagoRepository;

    @InjectMocks
    private EstadoPagoService estadoPagoService;

    private EstadoPago estadoPago;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        estadoPago = new EstadoPago();
        estadoPago.setEstadoPago("prueba");
    }

    @Test
    void getEstadosPagos() {
        when(estadoPagoRepository.findAll()).thenReturn(Arrays.asList(estadoPago));
        assertNotNull(estadoPagoService.getEstadosPago());
    }


    @Test
    void newEstadoPago() {
        //TEST CUANDO EL ID NO ES 0
        estadoPago.setIdEstadoPago(1);
        ResponseEntity<Object> responseWithNonZeroId = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNonZeroId.getStatusCode());
        assertTrue(responseWithNonZeroId.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS SON NULOS
        estadoPago.setIdEstadoPago(0);
        estadoPago.setEstadoPago(null);
        ResponseEntity<Object> responseWithNullEstadoPago = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullEstadoPago.getStatusCode());
        assertTrue(responseWithNullEstadoPago.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS ESTAN EN BLANCOS
        estadoPago.setEstadoPago("");
        ResponseEntity<Object> responseWithBlankEstadoPago = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankEstadoPago.getStatusCode());
        assertTrue(responseWithBlankEstadoPago.getBody() instanceof HashMap);

        // TEST DE COMPROBACION DE NUMEROS
        estadoPago.setEstadoPago("123");
        ResponseEntity<Object> responseWithNumericEstadoPago = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericEstadoPago.getStatusCode());
        assertTrue(responseWithNumericEstadoPago.getBody() instanceof HashMap);

        // TEST DE VALIDACION
        estadoPago.setEstadoPago("ValidEstadoPago");
        ResponseEntity<Object> responseWithEstadoPago = estadoPagoService.newEstadoPago(estadoPago);
        assertEquals(HttpStatus.CREATED, responseWithEstadoPago.getStatusCode());
        assertTrue(responseWithEstadoPago.getBody() instanceof HashMap);

    }

    @Test
    void updateEstadoPago() {
        // Configuración del escenario
        int idEstadoPagoExistente= 1;
        int idEstadoPagoNoExistente = 2; // ID que no existe

        when(estadoPagoRepository.existsById(idEstadoPagoExistente)).thenReturn(true);

        // Caso cuando el campo 'estadoPago' es nulo
        estadoPago.setIdEstadoPago(idEstadoPagoExistente);
        estadoPago.setEstadoPago(null);
        ResponseEntity<Object> responseWithNullEstadoPago = estadoPagoService.updateEstadoPago(idEstadoPagoExistente, estadoPago);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNullEstadoPago.getStatusCode());
        assertTrue(responseWithNullEstadoPago.getBody() instanceof HashMap);

        // Caso cuando el campo 'estadoPago' está en blanco
        estadoPago.setEstadoPago("");
        ResponseEntity<Object> responseWithBlankEstadoPago = estadoPagoService.updateEstadoPago(idEstadoPagoExistente, estadoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankEstadoPago.getStatusCode());
        assertTrue(responseWithBlankEstadoPago.getBody() instanceof HashMap);

        // Caso cuando el campo 'estadoPago' contiene números
        estadoPago.setEstadoPago("123");
        ResponseEntity<Object> responseWithNumericEstadoPago = estadoPagoService.updateEstadoPago(idEstadoPagoExistente, estadoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericEstadoPago.getStatusCode());
        assertTrue(responseWithNumericEstadoPago.getBody() instanceof HashMap);

        // Caso cuando la actualización es exitosa
        estadoPago.setEstadoPago("ValidEstadoPago");
        ResponseEntity<Object> responseWithValidEstadoPago = estadoPagoService.updateEstadoPago(idEstadoPagoExistente, estadoPago);
        assertEquals(HttpStatus.CREATED, responseWithValidEstadoPago.getStatusCode());
        assertTrue(responseWithValidEstadoPago.getBody() instanceof HashMap);

        // Caso cuando el ID no existe
        ResponseEntity<Object> responseWithNonExistingId = estadoPagoService.updateEstadoPago(idEstadoPagoNoExistente, estadoPago);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);

        // Verificar que el método save se haya llamado una vez
        verify(estadoPagoRepository, times(1)).save(estadoPago);


    }

    @Test
    void deleteEstadoPago() {
        // Configuración del repositorio mock
        int idToDelete = 1;
        when(estadoPagoRepository.existsById(idToDelete)).thenReturn(true);

        // Prueba cuando el ID existe
        ResponseEntity<Object> responseWithExistingId = estadoPagoService.deleteEstadoPago(idToDelete);
        assertEquals(HttpStatus.ACCEPTED, responseWithExistingId.getStatusCode());
        assertTrue(responseWithExistingId.getBody() instanceof HashMap);

        // Verificar que se llamó a deleteById con el ID proporcionado
        verify(estadoPagoRepository, times(1)).deleteById(idToDelete);

        // Prueba cuando el ID no existe
        int nonExistingId = 2;
        when(estadoPagoRepository.existsById(nonExistingId)).thenReturn(false);

        ResponseEntity<Object> responseWithNonExistingId = estadoPagoService.deleteEstadoPago(nonExistingId);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);
    }
}
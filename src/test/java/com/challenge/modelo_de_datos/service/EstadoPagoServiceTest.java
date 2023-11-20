package com.challenge.modelo_de_datos.service;
import com.challenge.modelo_de_datos.model.EstadoPago;
import com.challenge.modelo_de_datos.repository.EstadoPagoRepository;
import org.junit.jupiter.api.Assertions;
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



}
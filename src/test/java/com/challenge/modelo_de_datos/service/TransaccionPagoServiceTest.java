package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.*;
import com.challenge.modelo_de_datos.repository.EstadoPagoRepository;
import com.challenge.modelo_de_datos.repository.TipoPagoRepository;
import com.challenge.modelo_de_datos.repository.TransaccionPagoRepository;
import com.challenge.modelo_de_datos.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionPagoServiceTest {
    @Mock
    private TransaccionRepository transaccionRepository;
    @Mock
    private TransaccionPagoRepository transaccionPagoRepository;
    @Mock
    private EstadoPagoRepository estadoPagoRepository;
    @Mock
    private TipoPagoRepository tipoPagoRepository;
    @InjectMocks
    private TransaccionPagoService transaccionPagoService;

    private TransaccionPago transaccionPago;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transaccionPago= new TransaccionPago();
    }
    @Test
    void getTransaccionesPago() {
        when(transaccionPagoRepository.findAll()).thenReturn(Arrays.asList(transaccionPago));
        assertNotNull(transaccionPagoService.getTransaccionesPago());
    }
    /*Transaccion pago new*/
    @Test
    public void testNewTransaccionPago_Success() {
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setDescripcion("prueba");
        transaccionPago.setMonto_total(1000);
        transaccionPago.setEstadoPago(new EstadoPago());
        when(transaccionRepository.existsById(transaccionPago.getTransaccion().getIdTransaccion())).thenReturn(true);
        when(tipoPagoRepository.existsById(transaccionPago.getTipoPago().getIdTipoPago())).thenReturn(true);
        when(estadoPagoRepository.existsById(transaccionPago.getEstadoPago().getIdEstadoPago())).thenReturn(true);
        ResponseEntity<Object> response= transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito",((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, times(1)).save(transaccionPago);
    }
    @Test
    public void testNewTransaccionPago_BadRequest_InvalidId() {
        transaccionPago.setIdTransaccionPago(1);
        ResponseEntity<Object> response = transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testNewTransaccionPago_BadRequest_NullFields() {
        transaccionPago.setTipoPago(null);
        transaccionPago.setTransaccion(null);
        transaccionPago.setDescripcion(null);
        transaccionPago.setMonto_total(0);
        transaccionPago.setEstadoPago(null);
        ResponseEntity<Object> response = transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos obligatorios", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testNewTransaccionPago_Conflict_Blank() {
        transaccionPago.setDescripcion("");
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setMonto_total(12);
        transaccionPago.setTransaccion(new Transaccion());

        ResponseEntity<Object> response = transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testNewTransaccionPago_Conflict_Numeric() {
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setMonto_total(120);
        transaccionPago.setDescripcion("123");
        ResponseEntity<Object> response = transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testNewTransaccionPago_Monto_Menos(){
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setMonto_total(-1);
        transaccionPago.setDescripcion("prueba");
        ResponseEntity<Object> response = transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las cantidades numericas deben ser mayores a 0", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testNewTransaccionPago_invalid_TransaccionPago(){
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setMonto_total(120);
        transaccionPago.setDescripcion("prueba");
        when(transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())).thenReturn(false);
        ResponseEntity<Object> response = transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La Transaccion con ese ID no existe", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testNewTransaccionPago_invalid_EstadoPago(){
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setMonto_total(120);
        transaccionPago.setDescripcion("prueba");
        when(transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())).thenReturn(true);
        when(estadoPagoRepository.existsById(transaccionPago.getEstadoPago().getIdEstadoPago())).thenReturn(false);
        ResponseEntity<Object> response = transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El EstadoPago con ese ID no existe", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testNewTransaccionPago_invalid_TipoPago(){
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setMonto_total(120);
        transaccionPago.setDescripcion("prueba");
        when(transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())).thenReturn(true);
        when(estadoPagoRepository.existsById(transaccionPago.getEstadoPago().getIdEstadoPago())).thenReturn(true);
        when(tipoPagoRepository.existsById(transaccionPago.getTipoPago().getIdTipoPago())).thenReturn(false);
        ResponseEntity<Object> response = transaccionPagoService.newTransaccionPago(transaccionPago);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El TipoPago con ese ID no existe", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }

    /*fin transaccion pago*/
    /*update transaccion pago*/
    @Test
    public void testUpdateTransaccionPago_Success() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setDescripcion("prueba");
        transaccionPago.setMonto_total(1000);
        transaccionPago.setEstadoPago(new EstadoPago());

        when(transaccionPagoRepository.existsById(id)).thenReturn(true);
        when(transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())).thenReturn(true);
        when(tipoPagoRepository.existsById(transaccionPago.getTipoPago().getIdTipoPago())).thenReturn(true);
        when(estadoPagoRepository.existsById(transaccionPago.getEstadoPago().getIdEstadoPago())).thenReturn(true);

        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Se actualizó con éxito", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, times(1)).save(transaccionPago);
    }
    @Test
    public void testUpdateTransaccionPago_NullFields() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setTransaccion(null);
        transaccionPago.setTipoPago(null);
        transaccionPago.setDescripcion(null);
        transaccionPago.setMonto_total(0);
        transaccionPago.setEstadoPago(null);

        when(transaccionPagoRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos obligatorios", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testUpdateTransaccionPago_NotFound() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setDescripcion("prueba");
        transaccionPago.setMonto_total(100);
        when(transaccionPagoRepository.existsById(transaccionPago.getIdTransaccionPago())).thenReturn(true);
        when(transaccionPagoRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No existe la TransaccionPago con ese ID", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }

    @Test
    public void testUpdateTransaccionPago_BlankDescripcion() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setDescripcion(""); // Descripción en blanco
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setMonto_total(120);
        when(transaccionPagoRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }

    @Test
    public void testUpdateTransaccionPago_NumericDescripcion() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setEstadoPago(new EstadoPago());
        transaccionPago.setMonto_total(12);
        transaccionPago.setDescripcion("123"); // Descripción numérica
        when(transaccionPagoRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }

    @Test
    public void testUpdateTransaccionPago_MontoMenos() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setDescripcion("prueba");
        transaccionPago.setMonto_total(-1); // Monto negativo
        transaccionPago.setEstadoPago(new EstadoPago());
        when(transaccionPagoRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las cantidades numericas deben ser mayores a 0", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testUpdateTransaccionPago_InvalidTransaccionID() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setDescripcion("prueba");
        transaccionPago.setMonto_total(1000);
        transaccionPago.setEstadoPago(new EstadoPago());

        // Simula que no existe la Transaccion con el ID proporcionado
        when(transaccionPagoRepository.existsById(id)).thenReturn(true);
        when(transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())).thenReturn(false);

        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La Transaccion con ese ID no existe", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }

    @Test
    public void testUpdateTransaccionPago_InvalidEstadoPagoID() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setDescripcion("prueba");
        transaccionPago.setMonto_total(1000);
        transaccionPago.setEstadoPago(new EstadoPago());

        // Simula que no existe el EstadoPago con el ID proporcionado
        when(transaccionPagoRepository.existsById(id)).thenReturn(true);
        when(transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())).thenReturn(true);
        when(estadoPagoRepository.existsById(transaccionPago.getEstadoPago().getIdEstadoPago())).thenReturn(false);

        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El EstadoPago con ese ID no existe", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }
    @Test
    public void testUpdateTransaccionPago_InvalidTipoPagoID() {
        int id = 1;
        transaccionPago.setIdTransaccionPago(id);
        transaccionPago.setTransaccion(new Transaccion());
        transaccionPago.setTipoPago(new TipoPago());
        transaccionPago.setDescripcion("prueba");
        transaccionPago.setMonto_total(1000);
        transaccionPago.setEstadoPago(new EstadoPago());

        // Simula que no existe el TipoPago con el ID proporcionado
        when(transaccionPagoRepository.existsById(id)).thenReturn(true);
        when(transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())).thenReturn(true);
        when(estadoPagoRepository.existsById(transaccionPago.getEstadoPago().getIdEstadoPago())).thenReturn(true);
        when(tipoPagoRepository.existsById(transaccionPago.getTipoPago().getIdTipoPago())).thenReturn(false);

        ResponseEntity<Object> response = transaccionPagoService.updateTransaccionPago(id, transaccionPago);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El TipoPago con ese ID no existe", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).save(transaccionPago);
    }

    /*fin update transaccion pago*/
    @Test
    public void testDeleteTransaccionPago_Success() {
        int id = 1;
        when(transaccionPagoRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Object> response = transaccionPagoService.deleteTransaccionPago(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TransaccionPago eliminada", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteTransaccionPago_NotFound() {
        int id = 1;
        when(transaccionPagoRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Object> response = transaccionPagoService.deleteTransaccionPago(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No existe la TransaccionPago con ese ID", ((HashMap) response.getBody()).get("message"));
        verify(transaccionPagoRepository, never()).deleteById(id);
    }

}
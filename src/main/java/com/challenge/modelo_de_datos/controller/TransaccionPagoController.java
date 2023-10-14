package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Transaccion_pago;
import com.challenge.modelo_de_datos.service.TransaccionPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "transaccion_pago")
public class TransaccionPagoController {
    private final TransaccionPagoService transaccionPagoService;

    @Autowired
    public TransaccionPagoController(TransaccionPagoService transaccionPagoService) {
        this.transaccionPagoService = transaccionPagoService;
    }

    @GetMapping
    public List<Transaccion_pago> getTransaccionesPago() {
        return transaccionPagoService.getTransaccionesPago();
    }

    @PostMapping
    public ResponseEntity<Object> addTransaccionPago(@RequestBody Transaccion_pago transaccionPago) {
        return transaccionPagoService.newTransaccionPago(transaccionPago);
    }

    @PutMapping
    public ResponseEntity<Object> updateTransaccionPago(@RequestBody Transaccion_pago transaccionPago) {
        return transaccionPagoService.updateTransaccionPago(transaccionPago);
    }

    @DeleteMapping(path = "{idTransaccionPago}")
    public ResponseEntity<Object> deleteTransaccionPago(@PathVariable("idTransaccionPago") int id) {
        return transaccionPagoService.deleteTransaccionPago(id);
    }
}


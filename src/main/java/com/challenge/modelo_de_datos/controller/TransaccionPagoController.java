package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TransaccionPago;
import com.challenge.modelo_de_datos.service.TransaccionPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/TransaccionPago")
public class TransaccionPagoController {
    private final TransaccionPagoService transaccionPagoService;

    @Autowired
    public TransaccionPagoController(TransaccionPagoService transaccionPagoService) {
        this.transaccionPagoService = transaccionPagoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Jefe','TrabajadorAdmin','Trabajador','Comprador')")
    public List<TransaccionPago> getTransaccionesPago() {
        return transaccionPagoService.getTransaccionesPago();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasAnyRole('Admin','Comprador')")
    public ResponseEntity<Object> addTransaccionPago(@RequestBody @Valid TransaccionPago transaccionPago) {
        return transaccionPagoService.newTransaccionPago(transaccionPago);
    }

    @PutMapping(path = "/Update/{idTransaccionPago}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> updateTransaccionPago(@PathVariable("idTransaccionPago") @NotNull Integer id, @RequestBody @Valid TransaccionPago transaccionPago) {
        return transaccionPagoService.updateTransaccionPago(id,transaccionPago);
    }

    @DeleteMapping(path = "/Delete/{idTransaccionPago}")
    @PreAuthorize("hasRole('Admin','Jefe')")
    public ResponseEntity<Object> deleteTransaccionPago(@PathVariable("idTransaccionPago") @NotNull Integer id) {
        return transaccionPagoService.deleteTransaccionPago(id);
    }
}


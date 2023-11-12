package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.EstadoPago;
import com.challenge.modelo_de_datos.service.EstadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/EstadoPago")
public class EstadoPagoController {
    private final EstadoPagoService estadoPagoService;

    @Autowired
    public EstadoPagoController(EstadoPagoService estadoPagoService) {
        this.estadoPagoService = estadoPagoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Jefe','TrabajadorAdmin','Trabajador','Comprador')")
    public List<EstadoPago> getEstadosPago() {
        return estadoPagoService.getEstadosPago();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> addEstadoPago(@RequestBody @Valid EstadoPago estado_pago) {
        return estadoPagoService.newEstadoPago(estado_pago);
    }

    @PutMapping(path = "/Update/{idEstadoPago}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> updateEstadoPago(@PathVariable("idEstadoPago") @NotNull Integer id, @RequestBody @Valid EstadoPago estado_pago) {
        return estadoPagoService.updateEstadoPago(id,estado_pago);
    }

    @DeleteMapping(path = "/Delete/{idEstadoPago}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deleteEstadoPago(@PathVariable("idEstadoPago") @NotNull Integer id) {
        return estadoPagoService.deleteEstadoPago(id);
    }
}

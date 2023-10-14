package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Estado_pago;
import com.challenge.modelo_de_datos.service.EstadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "estado_pago")
public class EstadoPagoController {
    private final EstadoPagoService estadoPagoService;

    @Autowired
    public EstadoPagoController(EstadoPagoService estadoPagoService) {
        this.estadoPagoService = estadoPagoService;
    }

    @GetMapping
    public List<Estado_pago> getEstadosPago() {
        return estadoPagoService.getEstadosPago();
    }

    @PostMapping
    public ResponseEntity<Object> addEstadoPago(@RequestBody Estado_pago estado_pago) {
        return estadoPagoService.newEstadoPago(estado_pago);
    }

    @PutMapping
    public ResponseEntity<Object> updateEstadoPago(@RequestBody Estado_pago estado_pago) {
        return estadoPagoService.updateEstadoPago(estado_pago);
    }

    @DeleteMapping(path = "{idEstadoPago}")
    public ResponseEntity<Object> deleteEstadoPago(@PathVariable("idEstadoPago") int id) {
        return estadoPagoService.deleteEstadoPago(id);
    }
}

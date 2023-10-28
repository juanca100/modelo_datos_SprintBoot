package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.EstadoPago;
import com.challenge.modelo_de_datos.service.EstadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<EstadoPago> getEstadosPago() {
        return estadoPagoService.getEstadosPago();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addEstadoPago(@RequestBody EstadoPago estado_pago) {
        return estadoPagoService.newEstadoPago(estado_pago);
    }

    @PutMapping(path = "/Update/{idEstadoPago}")
    public ResponseEntity<Object> updateEstadoPago(@PathVariable("idEstadoPago") Integer id,@RequestBody EstadoPago estado_pago) {
        return estadoPagoService.updateEstadoPago(id,estado_pago);
    }

    @DeleteMapping(path = "/Delete/{idEstadoPago}")
    public ResponseEntity<Object> deleteEstadoPago(@PathVariable("idEstadoPago") Integer id) {
        return estadoPagoService.deleteEstadoPago(id);
    }
}

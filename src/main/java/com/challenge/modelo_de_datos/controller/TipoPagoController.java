package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TipoPago;
import com.challenge.modelo_de_datos.service.TipoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "tipo_pago")
public class TipoPagoController {
    private final TipoPagoService tipoPagoService;

    @Autowired
    public TipoPagoController(TipoPagoService tipoPagoService) {
        this.tipoPagoService = tipoPagoService;
    }

    @GetMapping
    public List<TipoPago> getTiposPago() {
        return tipoPagoService.getTiposPago();
    }

    @PostMapping
    public ResponseEntity<Object> addTipoPago(@RequestBody TipoPago tipoPago) {
        return tipoPagoService.newTipoPago(tipoPago);
    }

    @PutMapping
    public ResponseEntity<Object> updateTipoPago(@RequestBody TipoPago tipoPago) {
        return tipoPagoService.updateTipoPago(tipoPago);
    }

    @DeleteMapping(path = "{idTipoPago}")
    public ResponseEntity<Object> deleteTipoPago(@PathVariable("idTipoPago") int id) {
        return tipoPagoService.deleteTipoPago(id);
    }
}

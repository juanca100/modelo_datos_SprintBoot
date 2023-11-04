package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TipoPago;
import com.challenge.modelo_de_datos.model.TipoProducto;
import com.challenge.modelo_de_datos.service.TipoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/TipoPago")
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

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addTipoPago(@RequestBody @Valid TipoPago tipoPago) {
        return tipoPagoService.newTipoPago(tipoPago);
    }

    @PutMapping(path = "/Update/{idTipoPago}")
    public ResponseEntity<Object> updateTipoPago(@PathVariable("idTipoPago") @NotNull Integer id, @RequestBody  @Valid TipoPago tipoPago) {
        return tipoPagoService.updateTipoPago(id,tipoPago);
    }

    @DeleteMapping(path = "/Delete/{idTipoPago}")
    public ResponseEntity<Object> deleteTipoPago(@PathVariable("idTipoPago")@NotNull Integer id) {
        return tipoPagoService.deleteTipoPago(id);
    }
}
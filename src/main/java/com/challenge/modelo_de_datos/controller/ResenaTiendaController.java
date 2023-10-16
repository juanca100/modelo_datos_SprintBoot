package com.challenge.modelo_de_datos.controller;


import com.challenge.modelo_de_datos.model.ResenaTienda;
import com.challenge.modelo_de_datos.service.ResenaTiendaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.modelo_de_datos.model.ResenaProducto;
import com.challenge.modelo_de_datos.model.Transaccion;
import com.challenge.modelo_de_datos.service.ResenaProductoService;
import com.challenge.modelo_de_datos.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;
@RestController
@RequestMapping(path = "ResenasTiendas")

public class ResenaTiendaController {

    private final ResenaTiendaService resenaTiendaService;

    @Autowired
    public ResenaTiendaController(ResenaTiendaService resenaTiendaService) {
        this.resenaTiendaService = resenaTiendaService;
    }

    @GetMapping
    public List<ResenaTienda> getResenasTiendas(){
        return this.resenaTiendaService.getResenaTienda();
    }

    @PostMapping
    public ResponseEntity<Object> addResenaTienda(@RequestBody ResenaTienda resenaTienda){
        return this.resenaTiendaService.newResenaTienda(resenaTienda);
    }

    @PutMapping
    public ResponseEntity<Object>updateResena(@RequestBody ResenaTienda resenaTienda){
        return this.resenaTiendaService.updateResenaTienda(resenaTienda);
    }

    @DeleteMapping(path="{idResenaTienda}")
    public ResponseEntity<Object> deleteTransaccion(@PathVariable("idResenaTienda") Integer id){
        return this.resenaTiendaService.deleteResenaTieneda(id);
    }

}

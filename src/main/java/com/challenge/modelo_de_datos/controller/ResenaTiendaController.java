package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.ResenaTienda;
import com.challenge.modelo_de_datos.service.ResenaTiendaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/ResenaTienda")
public class ResenaTiendaController {

    private final ResenaTiendaService resenaTiendaService;

    @Autowired
    public ResenaTiendaController(ResenaTiendaService resenaTiendaService) {
        this.resenaTiendaService = resenaTiendaService;
    }

    @GetMapping
    public List<ResenaTienda> getResenasTienda(){
        return this.resenaTiendaService.getResenasTienda();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addResenaTienda(@RequestBody ResenaTienda resenaTienda){
        return this.resenaTiendaService.newResenaTienda(resenaTienda);
    }

    @PutMapping(path="/Update/{idResenaTienda}")
    public ResponseEntity<Object>updateResena(@PathVariable("idResenaTienda") Integer id,@RequestBody ResenaTienda resenaTienda){
        return this.resenaTiendaService.updateResenaTienda(id,resenaTienda);
    }

    @DeleteMapping(path="/Delete/{idResenaTienda}")
    public ResponseEntity<Object> deleteTransaccion(@PathVariable("idResenaTienda") Integer id){
        return this.resenaTiendaService.deleteResenaTieneda(id);
    }

}
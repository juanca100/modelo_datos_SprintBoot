package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.ResenaTienda;
import com.challenge.modelo_de_datos.service.ResenaTiendaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @PreAuthorize("hasAnyRole('Admin', 'Usuario','Comprador')")
    public ResponseEntity<Object> addResenaTienda(@RequestBody @Valid ResenaTienda resenaTienda){
        return this.resenaTiendaService.newResenaTienda(resenaTienda);
    }

    @PutMapping(path="/Update/{idResenaTienda}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object>updateResena(@PathVariable("idResenaTienda") @NotNull Integer id, @RequestBody @Valid ResenaTienda resenaTienda){
        return this.resenaTiendaService.updateResenaTienda(id,resenaTienda);
    }

    @DeleteMapping(path="/Delete/{idResenaTienda}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deleteTransaccion(@PathVariable("idResenaTienda") @NotNull Integer id){
        return this.resenaTiendaService.deleteResenaTieneda(id);
    }

}
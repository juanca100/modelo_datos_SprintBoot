package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Estado;
import com.challenge.modelo_de_datos.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Estado")
public class EstadoController {
    private final EstadoService estadoService;

    @Autowired
    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Usuario')")
    public List<Estado> getEstados() {
        return this.estadoService.getEstados();
    }

    @PostMapping(path = "/Create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> addEstado(@RequestBody @Valid Estado estado) {
        return this.estadoService.newEstado(estado);
    }

    @PutMapping(path="/Update/{idEstado}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object>updateEstado(@PathVariable("idEstado") @NotNull Integer id, @RequestBody @Valid Estado estado){
        return this.estadoService.updateEstado(id,estado);
    }

    @DeleteMapping(path="/Delete/{idEstado}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deleteEstado(@PathVariable("idEstado") @NotNull Integer id){
        return this.estadoService.deleteEstado(id);
    }
}
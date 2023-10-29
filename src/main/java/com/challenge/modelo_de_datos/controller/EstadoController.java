package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Estado;
import com.challenge.modelo_de_datos.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
    public List<Estado> getEstados() {
        return this.estadoService.getEstados();
    }

    @PostMapping(path = "/Create")
    public ResponseEntity<Object> addEstado(@RequestBody @Valid Estado estado) {
        return this.estadoService.newEstado(estado);
    }

    @PutMapping(path="/Update/{idEstado}")
    public ResponseEntity<Object>updateEstado(@PathVariable("idEstado") Integer id, @RequestBody @Valid Estado estado){
        return this.estadoService.updateEstado(id,estado);
    }

    @DeleteMapping(path="/Delete/{idEstado}")
    public ResponseEntity<Object> deleteEstado(@PathVariable("idEstado") Integer id){
        return this.estadoService.deleteEstado(id);
    }
}
package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Ciudad;
import com.challenge.modelo_de_datos.model.Estado;
import com.challenge.modelo_de_datos.service.CiudadService;
import com.challenge.modelo_de_datos.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Estado")
public class EstadoController {
    private final EstadoService estadoService;

    @Autowired
    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public List<Estado> getEstados(){
        return this.estadoService.getEstados();
    }

    @PostMapping
    public ResponseEntity<Object> addEstado(@RequestBody Estado estado){
        return this.estadoService.newEstado(estado);
    }

    @PutMapping
    public ResponseEntity<Object>updateEstado(@RequestBody Estado estado){
        return this.estadoService.updateEstado(estado);
    }

    @DeleteMapping(path="{idEstado}")
    public ResponseEntity<Object> deleteEstado(@PathVariable("idEstado") Integer id){
        return this.estadoService.deleteEstado(id);
    }
}



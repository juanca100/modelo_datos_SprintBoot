package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Comprador;
import com.challenge.modelo_de_datos.service.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Compradores")
public class CompradorController {
    private final CompradorService CompradorService;

    @Autowired
    public CompradorController(CompradorService compradorService) {
        CompradorService = compradorService;
    }

    @GetMapping
    public List<Comprador> getComprador(){
        return this.CompradorService.getCompradores();
    }

    @PostMapping
    public ResponseEntity<Object> addComprador(@RequestBody Comprador comprador){
        return this.CompradorService.newComprador(comprador);
    }

    @PutMapping
    public ResponseEntity<Object>updateComprador(@RequestBody Comprador comprador){
        return this.CompradorService.updateComprador(comprador);
    }

    @DeleteMapping(path="{idComprador}")
    public ResponseEntity<Object> deleteComprador(@PathVariable("idComprador") Integer id){
        return this.CompradorService.deleteComprados(id);
    }
}


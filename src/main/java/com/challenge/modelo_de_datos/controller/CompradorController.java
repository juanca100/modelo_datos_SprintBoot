package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Comprador;
import com.challenge.modelo_de_datos.service.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Comprador")
public class CompradorController {
    private final CompradorService CompradorService;

    @Autowired
    public CompradorController(CompradorService compradorService) {
        CompradorService = compradorService;
    }

    @GetMapping
    public List<Comprador> getCompradores(){
        return this.CompradorService.getCompradores();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addComprador(@RequestBody @Valid Comprador comprador){
        return this.CompradorService.newComprador(comprador);
    }

    @PutMapping(path="/Update/{idComprador}")
    public ResponseEntity<Object>updateComprador(@PathVariable("idComprador")@NotNull Integer id, @RequestBody @Valid Comprador comprador){
        return this.CompradorService.updateComprador(id,comprador);
    }

    @DeleteMapping(path="/Delete/{idComprador}")
    public ResponseEntity<Object> deleteComprador(@PathVariable("idComprador") @NotNull   Integer id){
        return this.CompradorService.deleteComprador(id);
    }
}


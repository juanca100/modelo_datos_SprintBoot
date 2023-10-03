package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Ciudad;
import com.challenge.modelo_de_datos.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Ciudad")
public class CiudadController {
    private final CiudadService ciudadService;

    @Autowired
    public CiudadController(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    @GetMapping
    public List<Ciudad> getCiudades(){
        return this.ciudadService.getCiudades();
    }

    @PostMapping
    public ResponseEntity<Object> addCiudad(@RequestBody Ciudad ciudad){
        return this.ciudadService.newCiudad(ciudad);
    }

    @PutMapping
    public ResponseEntity<Object>updateCiudad(@RequestBody Ciudad ciudad){
        return this.ciudadService.updateCiudad(ciudad);
    }

    @DeleteMapping(path="{idCiudad}")
    public ResponseEntity<Object> deleteCiudad(@PathVariable("idCiudad") Integer id){
        return this.ciudadService.deleteCiudad(id);
    }
}


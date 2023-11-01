package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Ciudad;
import com.challenge.modelo_de_datos.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Ciudad")
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

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addCiudad(@RequestBody @Valid Ciudad ciudad){
        return this.ciudadService.newCiudad(ciudad);
    }

    @PutMapping(path="/Update/{idCiudad}")
    public ResponseEntity<Object>updateCiudad(@PathVariable("idCiudad") @NotNull Integer id,@RequestBody @Valid Ciudad ciudad){
        return this.ciudadService.updateCiudad(id,ciudad);
    }

    @DeleteMapping(path="/Delete/{idCiudad}")
    public ResponseEntity<Object> deleteCiudad(@PathVariable("idCiudad") @NotNull Integer id){
        return this.ciudadService.deleteCiudad(id);
    }
}


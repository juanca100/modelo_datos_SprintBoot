package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Pais")
public class PaisController {
    private final PaisService paisService;

    @Autowired
    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Usuario')")
    public List<Pais> getPaises(){
        return this.paisService.getPaises();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> addPais(@RequestBody @Valid Pais pais){
        return this.paisService.newPais(pais);
    }

    @PutMapping(path="/Update/{idPais}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object>updatePais(@PathVariable("idPais") @NotNull Integer id, @RequestBody @Valid Pais pais){
        return this.paisService.updatePais(id,pais);
    }

    @DeleteMapping(path="/Delete/{idPais}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deletePais(@PathVariable("idPais") @NotNull Integer id){
        return this.paisService.deletePais(id);
    }
}




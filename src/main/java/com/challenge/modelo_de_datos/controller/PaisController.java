package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Pais")
public class PaisController {
    private final PaisService paisService;

    @Autowired
    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    public List<Pais> getPaises(){
        return this.paisService.getPaises();
    }

    @PostMapping
    public ResponseEntity<Object> addPais(@RequestBody Pais pais){
        return this.paisService.newPais(pais);
    }

    @PutMapping
    public ResponseEntity<Object>updatePais(@RequestBody Pais pais){
        return this.paisService.updatePais(pais);
    }

    @DeleteMapping(path="{idPais}")
    public ResponseEntity<Object> deletePais(@PathVariable("idPais") Integer id){
        return this.paisService.deletePais(id);
    }
}




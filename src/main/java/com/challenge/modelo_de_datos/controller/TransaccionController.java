package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Transaccion;
import com.challenge.modelo_de_datos.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Transaccion")
public class TransaccionController {
    private final TransaccionService transaccionService;

    @Autowired
    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping
    public List<Transaccion> getTransacciones(){
        return this.transaccionService.getTransacciones();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addTransacion(@RequestBody @Valid Transaccion transaccion){
        return this.transaccionService.newTransaccion(transaccion);
    }

    @PutMapping(path = "/Update/{idTransaccion}")
    public ResponseEntity<Object>updateTransaccion(@PathVariable("idTransaccion") @NotNull Integer id, @RequestBody @Valid Transaccion transaccion){
        return this.transaccionService.updateTransaccion(id,transaccion);
    }

    @DeleteMapping(path = "/Delete/{idTransaccion}")
    public ResponseEntity<Object> deleteTransaccion(@PathVariable("idTransaccion") @NotNull Integer id){
        return this.transaccionService.deleteTransaccion(id);
    }
}
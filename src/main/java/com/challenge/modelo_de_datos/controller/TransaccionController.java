package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Transaccion;
import com.challenge.modelo_de_datos.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Transacciones")
public class TransaccionController {
    private final TransaccionService transaccionService;

    @Autowired
    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping
    public List<Transaccion> getTransacciones(){
        return this.transaccionService.getTransaccion();
    }

    @PostMapping
    public ResponseEntity<Object> addTransacion(@RequestBody Transaccion transaccion){
        return this.transaccionService.newTransaccion(transaccion);
    }

    @PutMapping
    public ResponseEntity<Object>updateTransaccion(@RequestBody Transaccion transaccion){
        return this.transaccionService.updateTransaccion(transaccion);
    }

    @DeleteMapping(path="{idTransaccion}")
    public ResponseEntity<Object> deleteTransaccion(@PathVariable("idTransaccion") Integer id){
        return this.transaccionService.deleteTransaccion(id);
    }
}


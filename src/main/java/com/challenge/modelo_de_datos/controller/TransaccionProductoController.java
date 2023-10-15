package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Transaccion_producto;
import com.challenge.modelo_de_datos.service.TransaccionProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "transaccion_producto")
public class TransaccionProductoController {
    private final TransaccionProductoService transaccionProductoService;

    @Autowired
    public TransaccionProductoController(TransaccionProductoService transaccionProductoService) {
        this.transaccionProductoService = transaccionProductoService;
    }

    @GetMapping
    public List<Transaccion_producto> getTransaccionesProducto() {
        return transaccionProductoService.getTransaccionesProducto();
    }

    @PostMapping
    public ResponseEntity<Object> addTransaccionProducto(@RequestBody Transaccion_producto transaccionProducto) {
        return transaccionProductoService.newTransaccionProducto(transaccionProducto);
    }

    @PutMapping
    public ResponseEntity<Object> updateTransaccionProducto(@RequestBody Transaccion_producto transaccionProducto) {
        return transaccionProductoService.updateTransaccionProducto(transaccionProducto);
    }

    @DeleteMapping(path = "{idTransaccionProducto}")
    public ResponseEntity<Object> deleteTransaccionProducto(@PathVariable("idTransaccionProducto") int id) {
        return transaccionProductoService.deleteTransaccionProducto(id);
    }
}


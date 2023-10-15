package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TransaccionProducto;
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
    public List<TransaccionProducto> getTransaccionesProducto() {
        return transaccionProductoService.getTransaccionesProducto();
    }

    @PostMapping
    public ResponseEntity<Object> addTransaccionProducto(@RequestBody TransaccionProducto transaccionProducto) {
        return transaccionProductoService.newTransaccionProducto(transaccionProducto);
    }

    @PutMapping
    public ResponseEntity<Object> updateTransaccionProducto(@RequestBody TransaccionProducto transaccionProducto) {
        return transaccionProductoService.updateTransaccionProducto(transaccionProducto);
    }

    @DeleteMapping(path = "{idTransaccion}/{idProducto}")
    public ResponseEntity<Object> deleteTransaccionProducto(@PathVariable("idTransaccion") int idTransaccion,@PathVariable("idProducto") int idProducto) {
        return transaccionProductoService.deleteTransaccionProducto(idTransaccion,idProducto);
    }
}


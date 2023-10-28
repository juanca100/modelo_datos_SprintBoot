package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TransaccionProducto;
import com.challenge.modelo_de_datos.service.TransaccionProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/TransaccionProducto")
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

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addTransaccionProducto(@RequestBody TransaccionProducto transaccionProducto) {
        return transaccionProductoService.newTransaccionProducto(transaccionProducto);
    }

    @PutMapping(path = "/Update/{idTransaccion}/{idProducto}")
    public ResponseEntity<Object> updateTransaccionProducto(@PathVariable("idTransaccion") Integer idTransaccion,@PathVariable("idProducto") Integer idProducto,@RequestBody TransaccionProducto transaccionProducto) {
        return transaccionProductoService.updateTransaccionProducto(idTransaccion,idProducto,transaccionProducto);
    }

    @DeleteMapping(path = "/Delete/{idTransaccion}/{idProducto}")
    public ResponseEntity<Object> deleteTransaccionProducto(@PathVariable("idTransaccion") Integer idTransaccion,@PathVariable("idProducto") Integer idProducto) {
        return transaccionProductoService.deleteTransaccionProducto(idTransaccion,idProducto);
    }
}


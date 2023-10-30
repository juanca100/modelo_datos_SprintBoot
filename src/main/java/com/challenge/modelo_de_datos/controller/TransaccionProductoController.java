package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TransaccionProducto;
import com.challenge.modelo_de_datos.service.TransaccionProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public ResponseEntity<Object> addTransaccionProducto(@RequestBody @Valid TransaccionProducto transaccionProducto) {
        return transaccionProductoService.newTransaccionProducto(transaccionProducto);
    }

    @PutMapping(path = "/Update/Transaccion/{idTransaccion}/Producto/{idProducto}")
    public ResponseEntity<Object> updateTransaccionProducto(@PathVariable("idTransaccion") @NotNull Integer idTransaccion, @PathVariable("idProducto") @NotNull Integer idProducto, @RequestBody @Valid TransaccionProducto transaccionProducto) {
        return transaccionProductoService.updateTransaccionProducto(idTransaccion,idProducto,transaccionProducto);
    }

    @DeleteMapping(path = "/Delete/Transaccion/{idTransaccion}/Producto/{idProducto}")
    public ResponseEntity<Object> deleteTransaccionProducto(@PathVariable("idTransaccion") @NotNull  Integer idTransaccion,@PathVariable("idProducto") @NotNull Integer idProducto) {
        return transaccionProductoService.deleteTransaccionProducto(idTransaccion,idProducto);
    }
}
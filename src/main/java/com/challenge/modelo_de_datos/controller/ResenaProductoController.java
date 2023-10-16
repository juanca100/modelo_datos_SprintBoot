package com.challenge.modelo_de_datos.controller;
import com.challenge.modelo_de_datos.model.ResenaProducto;
import com.challenge.modelo_de_datos.model.Transaccion;
import com.challenge.modelo_de_datos.service.ResenaProductoService;
import com.challenge.modelo_de_datos.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "ResenasProductos")
public class ResenaProductoController {

    private final ResenaProductoService resenaProductoService;

    @Autowired
    public ResenaProductoController(ResenaProductoService resenaProductoService) {
        this.resenaProductoService = resenaProductoService;
    }

    @GetMapping
    public List<ResenaProducto> getResenasProducto(){
        return this.resenaProductoService.getResenaProducto();
    }

    @PostMapping
    public ResponseEntity<Object> addResenaProducto(@RequestBody ResenaProducto resenaProducto){
        return this.resenaProductoService.newResenaProducto(resenaProducto);
    }

    @PutMapping
    public ResponseEntity<Object>updateResena(@RequestBody ResenaProducto resenaProducto){
        return this.resenaProductoService.updateResenaProducto(resenaProducto);
    }

    @DeleteMapping(path="{idResenaProducto}")
    public ResponseEntity<Object> deleteTransaccion(@PathVariable("idResenaProducto") Integer id){
        return this.resenaProductoService.deleteResenaProducto(id);
    }
}

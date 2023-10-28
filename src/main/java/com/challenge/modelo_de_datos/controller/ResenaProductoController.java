package com.challenge.modelo_de_datos.controller;
import com.challenge.modelo_de_datos.model.ResenaProducto;
import com.challenge.modelo_de_datos.service.ResenaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "api/v1/ResenaProducto")
public class ResenaProductoController {

    private final ResenaProductoService resenaProductoService;

    @Autowired
    public ResenaProductoController(ResenaProductoService resenaProductoService) {
        this.resenaProductoService = resenaProductoService;
    }

    @GetMapping
    public List<ResenaProducto> getResenasProducto(){
        return this.resenaProductoService.getResenasProducto();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addResenaProducto(@RequestBody ResenaProducto resenaProducto){
        return this.resenaProductoService.newResenaProducto(resenaProducto);
    }

    @PutMapping(path="/Update/{idResenaProducto}")
    public ResponseEntity<Object>updateResena(@PathVariable("idResenaProducto") Integer id,@RequestBody ResenaProducto resenaProducto){
        return this.resenaProductoService.updateResenaProducto(id,resenaProducto);
    }

    @DeleteMapping(path="/Delete/{idResenaProducto}")
    public ResponseEntity<Object> deleteTransaccion(@PathVariable("idResenaProducto") Integer id){
        return this.resenaProductoService.deleteResenaProducto(id);
    }
}

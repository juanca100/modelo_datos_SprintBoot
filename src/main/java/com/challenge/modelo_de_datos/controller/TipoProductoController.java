package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Categoria;
import com.challenge.modelo_de_datos.model.TipoProducto;
import com.challenge.modelo_de_datos.service.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "tiposProducto")
public class TipoProductoController {
    private final TipoProductoService tipoProductoService;

    @Autowired
    public TipoProductoController(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @GetMapping
    public List<TipoProducto> getTiposProductos(){
        return this.tipoProductoService.getTiposProductos();
    }

    @PostMapping
    public ResponseEntity<Object> addTipoProducto(@RequestBody TipoProducto tipoProducto){
        return this.tipoProductoService.newTiposProductos(tipoProducto);
    }

    @PutMapping
    public ResponseEntity<Object>updateTipoProducto(@RequestBody TipoProducto tipoProducto){
        return this.tipoProductoService.updateTiposProducto(tipoProducto);
    }

    @DeleteMapping(path="{idTipoProducto}")
    public ResponseEntity<Object> deleteCategoria(@PathVariable("idTipoProducto") Integer id){
        return this.tipoProductoService.deleteTiposProducto(id);
    }
}

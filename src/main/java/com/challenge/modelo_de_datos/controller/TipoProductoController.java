package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TipoProducto;
import com.challenge.modelo_de_datos.service.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/TipoProducto")
public class TipoProductoController {
    private final TipoProductoService tipoProductoService;

    @Autowired
    public TipoProductoController(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @GetMapping
    public List<TipoProducto> getTiposProductos(){
        return this.tipoProductoService.getTiposProducto();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addTipoProducto(@RequestBody TipoProducto tipoProducto){
        return this.tipoProductoService.newTipoProducto(tipoProducto);
    }

    @PutMapping(path = "/Update/{idTipoProducto}")
    public ResponseEntity<Object>updateTipoProducto(@PathVariable("idTipoProducto") Integer id,@RequestBody TipoProducto tipoProducto){
        return this.tipoProductoService.updateTipoProducto(id,tipoProducto);
    }

    @DeleteMapping(path = "/Delete/{idTipoProducto}")
    public ResponseEntity<Object> deleteCategoria(@PathVariable("idTipoProducto") Integer id){
        return this.tipoProductoService.deleteTipoProducto(id);
    }
}

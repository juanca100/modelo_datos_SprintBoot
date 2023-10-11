package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Inventario;
import com.challenge.modelo_de_datos.service.InventarioService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Inventario")
public class InventarioController {
    private final InventarioService inventarioService;
    @Autowired
    public InventarioController(InventarioService inventarioService){
        this.inventarioService=inventarioService;
    }
    @GetMapping
    public List<Inventario> getInventario(){
        return this.inventarioService.getInventario();
    }
    @PostMapping
    public ResponseEntity<Object> addInventario(@RequestBody Inventario inventario){
        return  this.inventarioService.newInventario(inventario);
    }
    @PutMapping
    public ResponseEntity<Object> updateInventario(@RequestBody Inventario inventario){
        return this.inventarioService.updateInventario(inventario);
    }
    @DeleteMapping(path = "{idInventario}")
    public ResponseEntity<Object> deleteInventario(@PathVariable("idInventario") Integer id){
        return this.inventarioService.deleteInventario(id);
    }
}

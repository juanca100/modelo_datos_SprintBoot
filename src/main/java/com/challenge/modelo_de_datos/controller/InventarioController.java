package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Inventario;
import com.challenge.modelo_de_datos.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Inventario")
public class InventarioController {
    private final InventarioService inventarioService;
    @Autowired
    public InventarioController(InventarioService inventarioService){
        this.inventarioService=inventarioService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Jefe','TrabajadorAdmin','Trabajador')")
    public List<Inventario> getInventarios(){
        return this.inventarioService.getInventarios();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasAnyRole('Admin', 'Jefe','TrabajadorAdmin')")
    public ResponseEntity<Object> addInventario(@RequestBody @Valid Inventario inventario){
        return  this.inventarioService.newInventario(inventario);
    }

    @PutMapping(path = "/Update/{idInventario}")
    @PreAuthorize("hasAnyRole('Admin', 'Jefe','TrabajadorAdmin')")
    public ResponseEntity<Object> updateInventario(@PathVariable("idInventario")@NotNull Integer id, @RequestBody @Valid Inventario inventario){
        return this.inventarioService.updateInventario(id,inventario);
    }

    @DeleteMapping(path = "/Delete/{idInventario}")
    @PreAuthorize("hasAnyRole('Admin', 'Jefe')")
    public ResponseEntity<Object> deleteInventario(@PathVariable("idInventario")@NotNull Integer id){
        return this.inventarioService.deleteInventario(id);
    }
}

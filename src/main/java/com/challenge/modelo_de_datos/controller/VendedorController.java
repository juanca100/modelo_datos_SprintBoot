package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Comprador;
import com.challenge.modelo_de_datos.model.Vendedor;
import com.challenge.modelo_de_datos.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Vendedores")
public class VendedorController {
    private final VendedorService VendedorService;

    @Autowired
    public VendedorController(VendedorService vendedorService) {
        VendedorService = vendedorService;
    }
    @GetMapping
    public List<Vendedor> getVendedor(){
        return this.VendedorService.getVendedores();
    }

    @PostMapping
    public ResponseEntity<Object> addVendedor(@RequestBody Vendedor vendedor){
        return this.VendedorService.newVendedor(vendedor);
    }

    @PutMapping
    public ResponseEntity<Object>updateComprador(@RequestBody Vendedor vendedor){
        return this.VendedorService.updateVendedor(vendedor);
    }

    @DeleteMapping(path="{idVendedor}")
    public ResponseEntity<Object> deleteVendedor(@PathVariable("idVendedor") Integer id){
        return this.VendedorService.deleteVendedor(id);
    }
}


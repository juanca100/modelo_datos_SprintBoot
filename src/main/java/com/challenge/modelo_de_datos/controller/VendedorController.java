package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Vendedor;
import com.challenge.modelo_de_datos.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Vendedor")
public class VendedorController {
    private final VendedorService VendedorService;

    @Autowired
    public VendedorController(VendedorService vendedorService) {
        VendedorService = vendedorService;
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Usuario')")
    public List<Vendedor> getVendedores(){
        return this.VendedorService.getVendedores();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasAnyRole('Admin', 'Usuario')")
    public ResponseEntity<Object> addVendedor(@RequestBody @Valid Vendedor vendedor){
        return this.VendedorService.newVendedor(vendedor);
    }

    @PutMapping(path="/Update/{idVendedor}")
    @PreAuthorize("hasAnyRole('Admin', 'Jefe','TrabajadorAdmin')")
    public ResponseEntity<Object>updateComprador(@PathVariable("idVendedor") @NotNull Integer id, @RequestBody @Valid Vendedor vendedor){
        return this.VendedorService.updateVendedor(id,vendedor);
    }

    @DeleteMapping(path="/Delete/{idVendedor}")
    @PreAuthorize("hasAnyRole('Admin', 'Jefe')")
    public ResponseEntity<Object> deleteVendedor(@PathVariable("idVendedor") @NotNull Integer id){
        return this.VendedorService.deleteVendedor(id);
    }
}
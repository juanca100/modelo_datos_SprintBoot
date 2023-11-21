package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.model.Rol;
import com.challenge.modelo_de_datos.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Rol")
public class RolController {
    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public List<Rol> getPaises(){
        return this.rolService.getRoles();
    }
}

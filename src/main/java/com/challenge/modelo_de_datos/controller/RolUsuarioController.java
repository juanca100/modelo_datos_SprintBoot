package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.model.RolUsuario;
import com.challenge.modelo_de_datos.service.RolUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/RolUsuario")
public class RolUsuarioController {
    private final RolUsuarioService rolUsuarioService;

    @Autowired
    public RolUsuarioController(RolUsuarioService rolUsuarioService) {
        this.rolUsuarioService = rolUsuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public List<RolUsuario> getRolesUsuario(){
        return this.rolUsuarioService.getRolesUsuario();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> addRolUsuario(@RequestBody @Valid RolUsuario rolUsuario){
        return this.rolUsuarioService.newRolUsuario(rolUsuario);
    }

    @DeleteMapping(path="/Delete/Rol/{idRol}/Usuario/{idUsuario}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deleteRolUsuario(@PathVariable("idRol") @NotNull  Integer idRol,@PathVariable("idUsuario") @NotNull Integer idUsuario) {
        return this.rolUsuarioService.deleteRolUsuario(idRol,idUsuario);
    }
}

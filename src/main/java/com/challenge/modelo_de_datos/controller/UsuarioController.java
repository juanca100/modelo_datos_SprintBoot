package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getUsuarios(){
        return this.usuarioService.getUsuarios();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object>addUsuario(@RequestBody @Valid Usuario usuario){
        return this.usuarioService.newUsuario(usuario);
    }

    @PutMapping(path="/Update/{idUsuario}")
    public ResponseEntity<Object>updateUsuario(@PathVariable("idUsuario") @NotNull Integer id, @RequestBody @Valid Usuario usuario){
        return this.usuarioService.updateUsuario(id,usuario);
    }

    @DeleteMapping(path="/Delete/{idUsuario}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable("idUsuario") @NotNull Integer id){
        return this.usuarioService.deleteUsuario(id);
    }
}
package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "usuarios")
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

    @PostMapping
    public ResponseEntity<Object>addUsuario(@RequestBody Usuario usuario){
        return this.usuarioService.newUsuario(usuario);
    }

    @PutMapping
    public ResponseEntity<Object>aupdateUsuario(@RequestBody Usuario usuario){
        return this.usuarioService.updateUsuario(usuario);
    }

    @DeleteMapping(path="{idUsuario}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable("idUsuario") Integer id){
        return this.usuarioService.deleteUsuario(id);
    }
}
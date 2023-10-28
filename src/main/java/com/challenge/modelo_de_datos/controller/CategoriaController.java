package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Categoria;
import com.challenge.modelo_de_datos.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> getCategorias(){
        return this.categoriaService.getCategorias();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addCategoria(@RequestBody Categoria categoria){
        return this.categoriaService.newCategoria(categoria);
    }

    @PutMapping(path="/Update/{idCategoria}")
    public ResponseEntity<Object>updateCategoria(@PathVariable("idCategoria") Integer id,@RequestBody Categoria categoria){
        return this.categoriaService.updateCategoria(id,categoria);
    }

    @DeleteMapping(path="/Delete/{idCategoria}")
    public ResponseEntity<Object> deleteCategoria(@PathVariable("idCategoria") Integer id){
        return this.categoriaService.deleteCategoria(id);
    }
}

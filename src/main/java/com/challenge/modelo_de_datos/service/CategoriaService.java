package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Categoria;
import com.challenge.modelo_de_datos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> getCategorias(){
        return this.categoriaRepository.findAll();
    }

    public ResponseEntity<Object> newCategoria(Categoria categoria) {
        HashMap<String,Object> datos= new HashMap<>();
        datos.put("message","Se guardo con exito");
        categoriaRepository.save(categoria);
        datos.put("data",categoria);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateCategoria(Categoria categoria) {
        HashMap<String,Object> datos= new HashMap<>();
        datos.put("message","Se actualizo con exito");
        categoriaRepository.save(categoria);
        datos.put("data",categoria);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteCategoria(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existe=this.categoriaRepository.existsById(id);
        if(!existe){
            datos.put("error",true);
            datos.put("message","No existe la categoria con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        categoriaRepository.deleteById(id);
        datos.put("message","Categoria eliminada");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }

}


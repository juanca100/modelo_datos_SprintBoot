package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TipoProducto;
import com.challenge.modelo_de_datos.repository.CategoriaRepository;
import com.challenge.modelo_de_datos.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TipoProductoService {
    private final TipoProductoRepository TipoProductoRepository;
    private final CategoriaRepository CategoriaRepository;

    @Autowired
    public TipoProductoService(TipoProductoRepository tipoProductoRepository, CategoriaRepository categoriaRepository) {
        TipoProductoRepository = tipoProductoRepository;
        CategoriaRepository = categoriaRepository;
    }

    public List<TipoProducto> getTiposProducto()
    {
        return this.TipoProductoRepository.findAll();
    }

    public ResponseEntity<Object> newTipoProducto(TipoProducto tipoProducto) {
        HashMap<String,Object> datos= new HashMap<>();
        Integer id=tipoProducto.getIdTipoProducto();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(tipoProducto.getTipoProducto()==null||tipoProducto.getCategoria()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if (tipoProducto.getTipoProducto().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (tipoProducto.getTipoProducto().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if(!CategoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria())){
            datos.put("error", true);
            datos.put("message", "La categoria no existe, ID erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
        datos.put("message", "Se guardó con éxito");
        TipoProductoRepository.save(tipoProducto);
        datos.put("data", tipoProducto);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateTipoProducto(Integer id,TipoProducto tipoProducto) {
        HashMap<String,Object> datos= new HashMap<>();
        if(tipoProducto.getTipoProducto()==null||tipoProducto.getCategoria()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(!TipoProductoRepository.existsById(id)){
            datos.put("error", true);
            datos.put("message","El id del tipo de producto proporcionado es erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        tipoProducto.setIdTipoProducto(id);
        if (tipoProducto.getTipoProducto().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (tipoProducto.getTipoProducto().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if(!CategoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria())){
            datos.put("error", true);
            datos.put("message", "La categoria no existe, ID erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
        datos.put("message", "Se guardó con éxito");
        TipoProductoRepository.save(tipoProducto);
        datos.put("data", tipoProducto);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteTipoProducto(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!TipoProductoRepository.existsById(id)){
            datos.put("error",true);
            datos.put("message","No existe el tipo de producto con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        TipoProductoRepository.deleteById(id);
        datos.put("message","Tipo eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}
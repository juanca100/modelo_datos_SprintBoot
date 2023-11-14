package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PaisService {

    private final PaisRepository paisRepository;

    @Autowired
    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    public List<Pais> getPaises(){
        return this.paisRepository.findAll();
    }

    public ResponseEntity<Object> newPais(Pais pais) {
        HashMap<String,Object> datos= new HashMap<>();
        Integer id=pais.getIdPais();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(pais.getPais()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if (pais.getPais().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (pais.getPais().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        datos.put("message","Se guardo con exito");
        paisRepository.save(pais);
        datos.put("data",pais);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updatePais(Integer id,Pais pais) {
        HashMap<String,Object> datos= new HashMap<>();
        if(pais.getPais()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(!paisRepository.existsById(id)){
            datos.put("error", true);
            datos.put("message", "El pais no existe, ID erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        pais.setIdPais(id);
        if (pais.getPais().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (pais.getPais().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        datos.put("message","Se actualizo con exito");
        paisRepository.save(pais);
        datos.put("data",pais);
        return new ResponseEntity<>(datos, HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deletePais(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!paisRepository.existsById(id)){
            datos.put("error",true);
            datos.put("message","No existe el pais con ese id");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT
            );
        }
        paisRepository.deleteById(id);
        datos.put("message","Pais eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED
        );
    }

}

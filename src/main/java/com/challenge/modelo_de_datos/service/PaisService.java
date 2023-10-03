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
        datos.put("message","Se guardo con exito");
        paisRepository.save(pais);
        datos.put("data",pais);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updatePais(Pais pais) {
        HashMap<String,Object> datos= new HashMap<>();
        datos.put("message","Se actualizo con exito");
        paisRepository.save(pais);
        datos.put("data",pais);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deletePais(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existe=this.paisRepository.existsById(id);
        if(!existe){
            datos.put("error",true);
            datos.put("message","No existe el pais con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        paisRepository.deleteById(id);
        datos.put("message","Pais eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }

}

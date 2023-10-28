package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Ciudad;
import com.challenge.modelo_de_datos.repository.CiudadRepository;
import com.challenge.modelo_de_datos.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CiudadService {
    private final CiudadRepository ciudadRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public CiudadService(CiudadRepository ciudadRepository, EstadoRepository estadoRepository) {
        this.ciudadRepository = ciudadRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Ciudad> getCiudades(){
        return this.ciudadRepository.findAll();
    }

    public ResponseEntity<Object> newCiudad(Ciudad ciudad) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existeEstado = this.estadoRepository.existsById(ciudad.getEstado().getIdEstado());
        if (existeEstado) {
            datos.put("message", "Se guardo con exito");
            ciudadRepository.save(ciudad);
            datos.put("data", ciudad);
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        } else {
            datos.put("message", "El estado no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
    }

    public ResponseEntity<Object> updateCiudad (Integer id,Ciudad ciudad) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeEstado=this.estadoRepository.existsById(ciudad.getEstado().getIdEstado());
        boolean existeCiudad=this.ciudadRepository.existsById(id);
        if(existeCiudad){
            if(existeEstado){
                datos.put("message","Se actualizo con exito");
                ciudadRepository.save(ciudad);
                datos.put("data",ciudad);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El estado con ese no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
        else{
            datos.put("message","No existe ciudad con ese ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> deleteCiudad(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeCiudad=this.ciudadRepository.existsById(id);
        if(!existeCiudad){
            datos.put("error",true);
            datos.put("message","No existe la ciudad con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        ciudadRepository.deleteById(id);
        datos.put("message","Ciudad eliminada");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}
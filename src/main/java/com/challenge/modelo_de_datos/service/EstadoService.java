package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Estado;
import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.repository.EstadoRepository;
import com.challenge.modelo_de_datos.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class EstadoService {
    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public EstadoService(PaisRepository paisRepository, EstadoRepository estadoRepository) {
        this.paisRepository = paisRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> getEstados(){
        return this.estadoRepository.findAll();
    }

    public ResponseEntity<Object> newEstado(Estado estado) {
        HashMap<String, Object> datos = new HashMap<>();
        Pais pais = estado.getPais();
        if (pais != null) {
            boolean existePais = this.paisRepository.existsById(pais.getIdPais());
            if (existePais) {
                datos.put("message", "Se guardó con éxito");
                estadoRepository.save(estado);
                datos.put("data", estado);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            } else {
                datos.put("message", "El país no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
        } else {
            datos.put("message", "El Pais en Estado es nulo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    public ResponseEntity<Object> updateEstado (Integer id,Estado estado) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeEstado=this.estadoRepository.existsById(id);
        boolean existePais = this.paisRepository.existsById(estado.getPais().getIdPais());
        if(existeEstado){
            if(existePais){
                datos.put("message","Se actualizo con exito");
                estadoRepository.save(estado);
                datos.put("data",estado);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El id de pais no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
        else{
            datos.put("message","El estado no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> deleteEstado(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeEstado=this.estadoRepository.existsById(id);
        if(!existeEstado){
            datos.put("error",true);
            datos.put("message","No existe estado con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        estadoRepository.deleteById(id);
        datos.put("message","Estado eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}

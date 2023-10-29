package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Estado;
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
        Integer id=estado.getIdEstado();
        if(id!=null){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(estado.getEstado()==null||estado.getPais()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        } else {
            if (estado.getEstado().isBlank()) {
                datos.put("error", true);
                datos.put("message", "Los campos de caracteres no deben estar vacios");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            } else {
                if (estado.getEstado().matches("\\d+")) {
                    datos.put("error", true);
                    datos.put("message", "Las campos de caracteres no deben ser numeros");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                } else {
                    if (!paisRepository.existsById(estado.getPais().getIdPais())) {
                        datos.put("error", true);
                        datos.put("message", "El pais no existe, ID erroneo");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CREATED
                        );
                    }
                    datos.put("message", "Se guardó con éxito");
                    estadoRepository.save(estado);
                    datos.put("data", estado);
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CREATED
                    );
                }
            }
        }
    }

    public ResponseEntity<Object> updateEstado (Integer id,Estado estado) {
        HashMap<String,Object> datos= new HashMap<>();
        if(estado.getEstado()==null||estado.getPais()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        } else {
            if(estadoRepository.existsById(id)) {
                estado.setIdEstado(id);
                if (estado.getEstado().isBlank()) {
                    datos.put("error", true);
                    datos.put("message", "Los campos de caracteres no deben estar vacios");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                } else {
                    if (estado.getEstado().matches("\\d+")) {
                        datos.put("error", true);
                        datos.put("message", "Las campos de caracteres no deben ser numeros");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );
                    } else {
                        if (!paisRepository.existsById(estado.getPais().getIdPais())) {
                            datos.put("error", true);
                            datos.put("message", "El id del pais es erroneo");
                            return new ResponseEntity<>(
                                    datos,
                                    HttpStatus.CONFLICT
                            );
                        }
                        datos.put("message", "Se actualizo con exito");
                        estadoRepository.save(estado);
                        datos.put("data", estado);
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CREATED
                        );
                    }
                }
            }
            else{
                datos.put("message","El id del estado proporcionado es erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    public ResponseEntity<Object> deleteEstado(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!estadoRepository.existsById(id)){
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
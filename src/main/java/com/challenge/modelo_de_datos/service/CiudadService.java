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

        Integer id=ciudad.getIdCiudad();
        if (id!=0){
            datos.put("error",true);
            datos.put("message","No mandar ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(ciudad.getCiudad()==null||ciudad.getEstado()==null){

            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );

        }else{

            if(ciudad.getCiudad().isBlank()){
                datos.put("error",true);
                datos.put("message", "Los campos de caracteres no deben estar vacios");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }else{
                if (ciudad.getCiudad().matches("\\d+")) {
                    datos.put("error", true);
                    datos.put("message", "Las campos de caracteres no deben ser numeros");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
            }else{
                    if(!estadoRepository.existsById(ciudad.getEstado().getIdEstado())){
                        datos.put("error", true);
                        datos.put("message", "El pais no existe, ID erroneo");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CREATED
                        );
                    }
                    datos.put("message","SE GUARDO LA CIUDAD CON EXITO");
                    ciudadRepository.save(ciudad);
                    datos.put("data",ciudad);
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CREATED
                    );

                }
            }

        }
    }

    public ResponseEntity<Object> updateCiudad (Integer id,Ciudad ciudad) {
        HashMap<String,Object> datos= new HashMap<>();
        if(ciudad.getCiudad()==null||ciudad.getEstado()==null){
            datos.put("error",true);
            datos.put("message","INGRESA TODOS LOS CAMPOS DE LA TABLA");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }else{
            if(ciudadRepository.existsById(id)){
                ciudad.setIdCiudad(id);

                if(ciudad.getCiudad().isBlank()){
                    datos.put("error",true);
                    datos.put("message","LOS CAMPOS DE CARACTERES NO DEBEN ESTAR VACIOS");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );

                }else{
                    if (ciudad.getCiudad().matches("\\d+")) {
                        datos.put("error", true);
                        datos.put("message", "Las campos de caracteres no deben ser numeros");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );
                }else{
                        if (!estadoRepository.existsById(ciudad.getEstado().getIdEstado())) {
                            datos.put("error", true);
                            datos.put("message", "El pais no existe,ID erroneo");
                            return new ResponseEntity<>(
                                    datos,
                                    HttpStatus.CONFLICT
                            );
                    }
                        datos.put("message","SE ACTUALIZO CON EXITO");
                        ciudadRepository.save(ciudad);
                        datos.put("data",ciudad);
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CREATED
                        );
                }
            }
        }else{
                datos.put("error", true);
                datos.put("message","El id de la ciudad proporcionado es erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    public ResponseEntity<Object> deleteCiudad(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!ciudadRepository.existsById(id)){
            datos.put("error",true);
            datos.put("message","No existe estado con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        ciudadRepository.deleteById(id);
        datos.put("message","CIUDAD ELIMINADA");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}
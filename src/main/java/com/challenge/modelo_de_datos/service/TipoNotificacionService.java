package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TipoNotificacion;
import com.challenge.modelo_de_datos.repository.TipoNotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class TipoNotificacionService {
    private final TipoNotificacionRepository tipoNotificacionRepository;

    @Autowired
    public TipoNotificacionService(TipoNotificacionRepository tipoNotificacionRepository) {
        this.tipoNotificacionRepository = tipoNotificacionRepository;
    }

    public List<TipoNotificacion> getTiposNotificaciones(){
        return this.tipoNotificacionRepository.findAll();
    }

    public ResponseEntity<Object> newTipoNotificacion(TipoNotificacion tiponotificacion) {
        HashMap<String,Object> datos= new HashMap<>();
        Integer id=tiponotificacion.getIdTipoNotificacion();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(tiponotificacion.getTipoNotificacion()==null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla excepto el ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        else{
            if (tiponotificacion.getTipoNotificacion().isBlank()) {
                datos.put("error", true);
                datos.put("message", "Los campos de texto no deben estar vacios");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
            else{
                if (tiponotificacion.getTipoNotificacion().matches("\\d+")) {
                    datos.put("error", true);
                    datos.put("message", "Las campos de texto no deben ser numeros");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                else{
                    datos.put("message","Se guardo con exito");
                    tipoNotificacionRepository.save(tiponotificacion);
                    datos.put("data",tiponotificacion);
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CREATED
                    );
                }
            }
        }
    }

    public ResponseEntity<Object> updateTipoNotificacion(Integer id,TipoNotificacion tiponotificacion) {
        HashMap<String,Object> datos= new HashMap<>();
        if(tiponotificacion.getTipoNotificacion()==null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla excepto el ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        else{
            if(this.tipoNotificacionRepository.existsById(id)){
                tiponotificacion.setIdTipoNotificacion(id);
                if (tiponotificacion.getTipoNotificacion().isBlank()) {
                    datos.put("error", true);
                    datos.put("message", "Los campos de caracteres no deben estar vacios");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                else {
                    if (tiponotificacion.getTipoNotificacion().matches("\\d+")) {
                        datos.put("error", true);
                        datos.put("message", "Las campos de caracteres no deben ser numeros");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );
                    } else {
                        datos.put("message","Se actualizo con exito");
                        tipoNotificacionRepository.save(tiponotificacion);
                        datos.put("data",tiponotificacion);
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CREATED
                        );
                    }
                }
            }
            else{
                datos.put("error",true);
                datos.put("message","El id proporcionado del T.Notificacion es erroeno");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    public ResponseEntity<Object> deleteTipoNotificacion(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!tipoNotificacionRepository.existsById(id)){
            datos.put("error",true);
            datos.put("message","No existe el tipo de notificacion con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        tipoNotificacionRepository.deleteById(id);
        datos.put("message","Tipo de notificacion eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}
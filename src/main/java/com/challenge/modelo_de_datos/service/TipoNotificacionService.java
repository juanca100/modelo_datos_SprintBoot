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
        datos.put("message","Se guardo con exito");
        tipoNotificacionRepository.save(tiponotificacion);
        datos.put("data",tiponotificacion);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateTipoNotificacion(Integer id,TipoNotificacion tiponotificacion) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeTN=this.tipoNotificacionRepository.existsById(id);
        if(!existeTN){
            datos.put("error",true);
            datos.put("message","No existe el tipo de notificacion con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        tiponotificacion.setIdTipoNotificacion(id);
        datos.put("message","Se actualizo con exito");
        tipoNotificacionRepository.save(tiponotificacion);
        datos.put("data",tiponotificacion);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteTipoNotificacion(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeTN=this.tipoNotificacionRepository.existsById(id);
        if(!existeTN){
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

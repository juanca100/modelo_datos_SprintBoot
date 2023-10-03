package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Notificacion;
import com.challenge.modelo_de_datos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoNotificacionRepository tipoNotificacionRepository;

    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository, UsuarioRepository usuarioRepository, TipoNotificacionRepository tipoNotificacionRepository) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoNotificacionRepository = tipoNotificacionRepository;
    }

    public List<Notificacion> getNotificacion(){
        return this.notificacionRepository.findAll();
    }

    public ResponseEntity<Object> newNotificacion(Notificacion notificacion) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeTipoN=this.tipoNotificacionRepository.existsById(notificacion.getTipoNotificacion().getIdTipoNotificacion());
        boolean existeUsuario=this.usuarioRepository.existsById(notificacion.getUsuario().getIdUsuario());
        if(existeTipoN){
            if(existeUsuario){
                datos.put("message","Se guardo con exito");
                notificacionRepository.save(notificacion);
                datos.put("data",notificacion);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El usuario no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
        }
        else{
            datos.put("message","El tipo de notificacion no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
    }

    public ResponseEntity<Object> updateNotificacion(Notificacion notificacion) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeTipoN=this.tipoNotificacionRepository.existsById(notificacion.getTipoNotificacion().getIdTipoNotificacion());
        boolean existeUsuario=this.usuarioRepository.existsById(notificacion.getUsuario().getIdUsuario());
        if(existeTipoN){
            if(existeUsuario){
                datos.put("message","Se actualizo con exito");
                notificacionRepository.save(notificacion);
                datos.put("data",notificacion);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El usuario no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
        }
        else{
            datos.put("message","El tipo de notificacion no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
    }

    public ResponseEntity<Object> deleteNotificacion(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existe=this.notificacionRepository.existsById(id);
        if(!existe){
            datos.put("error",true);
            datos.put("message","No hay notificacion con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        notificacionRepository.deleteById(id);
        datos.put("message","Notifiacion eliminada");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}


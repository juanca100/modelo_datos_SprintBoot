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

    public List<Notificacion> getNotificaciones(){
        return this.notificacionRepository.findAll();
    }

    public ResponseEntity<Object> newNotificacion(Notificacion notificacion) {
        HashMap<String,Object> datos= new HashMap<>();
        Integer id=notificacion.getIdNotificacion();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(notificacion.getDescripcion()==null||notificacion.getUsuario()==null||notificacion.getTipoNotificacion()==null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla excepto el ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        else{
            if (notificacion.getDescripcion().isBlank()) {
                datos.put("error", true);
                datos.put("message", "Los campos de texto no deben estar vacios");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
            else{
                if (notificacion.getDescripcion().matches("\\d+")) {
                    datos.put("error", true);
                    datos.put("message", "Las campos de texto no deben ser numeros");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                else{
                    if(tipoNotificacionRepository.existsById(notificacion.getTipoNotificacion().getIdTipoNotificacion())){
                        if(usuarioRepository.existsById(notificacion.getUsuario().getIdUsuario())){
                            datos.put("message","Se guardo con exito");
                            notificacionRepository.save(notificacion);
                            datos.put("data",notificacion);
                            return new ResponseEntity<>(
                                    datos,
                                    HttpStatus.CREATED
                            );
                        }
                        else{
                            datos.put("error", true);
                            datos.put("message","El usuario no existe,ID erroneo");
                            return new ResponseEntity<>(
                                    datos,
                                    HttpStatus.CONFLICT
                            );
                        }
                    }
                    else{
                        datos.put("error", true);
                        datos.put("message","El tipo de notificacion no existe,ID erroneo");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );
                    }
                }
            }
        }
    }

    public ResponseEntity<Object> updateNotificacion(Integer id,Notificacion notificacion) {
        HashMap<String,Object> datos= new HashMap<>();
        if(notificacion.getDescripcion()==null||notificacion.getUsuario()==null||notificacion.getTipoNotificacion()==null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla excepto el ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        else{
            if(notificacionRepository.existsById(id)){
                notificacion.setIdNotificacion(id);
                if (notificacion.getDescripcion().isBlank()) {
                    datos.put("error", true);
                    datos.put("message", "Los campos de texto no deben estar vacios");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                else{
                    if (notificacion.getDescripcion().matches("\\d+")) {
                        datos.put("error", true);
                        datos.put("message", "Las campos de texto no deben ser numeros");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );
                    }
                    else{
                        if(tipoNotificacionRepository.existsById(notificacion.getTipoNotificacion().getIdTipoNotificacion())){
                            if(usuarioRepository.existsById(notificacion.getUsuario().getIdUsuario())){
                                datos.put("message","Se actualizo con exito");
                                notificacionRepository.save(notificacion);
                                datos.put("data",notificacion);
                                return new ResponseEntity<>(
                                        datos,
                                        HttpStatus.CREATED
                                );
                            }
                            else{
                                datos.put("error", true);
                                datos.put("message","El usuario no existe,ID erroneo");
                                return new ResponseEntity<>(
                                        datos,
                                        HttpStatus.CONFLICT
                                );
                            }
                        }
                        else{
                            datos.put("error", true);
                            datos.put("message","El tipo de notificacion no existe,ID erroneo");
                            return new ResponseEntity<>(
                                    datos,
                                    HttpStatus.CONFLICT
                            );
                        }
                    }
                }
            }
            else{
                datos.put("error",true);
                datos.put("message","El id proporcionado de la notificacion es erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    public ResponseEntity<Object> deleteNotificacion(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!notificacionRepository.existsById(id)){
            datos.put("error",true);
            datos.put("message","No existe la notificacion con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        notificacionRepository.deleteById(id);
        datos.put("message","Notificacion eliminada");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}
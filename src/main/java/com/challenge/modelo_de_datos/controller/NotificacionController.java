package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Notificacion;
import com.challenge.modelo_de_datos.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Notificacion")
public class NotificacionController {
    private final NotificacionService notificacionService;

    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public List<Notificacion> getNotificaciones(){
        return this.notificacionService.getNotificaciones();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addNotificacion(@RequestBody Notificacion notificacion){
        return this.notificacionService.newNotificacion(notificacion);
    }

    @PutMapping(path="/Update/{idNotificacion}")
    public ResponseEntity<Object>updateNotificacion(@PathVariable("idNotificacion") Integer id,@RequestBody Notificacion notificacion){
        return this.notificacionService.updateNotificacion(id,notificacion);
    }

    @DeleteMapping(path="/Delete/{idNotificacion}")
    public ResponseEntity<Object> deleteTipoN(@PathVariable("idNotificacion") Integer id){
        return this.notificacionService.deleteNotificacion(id);
    }
}

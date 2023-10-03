package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Notificacion;
import com.challenge.modelo_de_datos.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Notificaciones")
public class NotificacionController {
    private final NotificacionService notificacionService;

    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public List<Notificacion> getNotificaciones(){
        return this.notificacionService.getNotificacion();
    }

    @PostMapping
    public ResponseEntity<Object> addNotificacion(@RequestBody Notificacion notificacion){
        return this.notificacionService.newNotificacion(notificacion);
    }

    @PutMapping
    public ResponseEntity<Object>updateNotificacion(@RequestBody Notificacion notificacion){
        return this.notificacionService.updateNotificacion(notificacion);
    }

    @DeleteMapping(path="{idNotificacion}")
    public ResponseEntity<Object> deleteTipoN(@PathVariable("idNotificacion") Integer id){
        return this.notificacionService.deleteNotificacion(id);
    }
}

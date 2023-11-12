package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Notificacion;
import com.challenge.modelo_de_datos.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @PreAuthorize("hasAnyRole('Admin','Usuario','Jefe','TrabajadorAdmin','Comprador')")
    public List<Notificacion> getNotificaciones(){
        return this.notificacionService.getNotificaciones();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasAnyRole('Admin','Jefe','TrabajadorAdmin')")
    public ResponseEntity<Object> addNotificacion(@RequestBody @Valid Notificacion notificacion){
        return this.notificacionService.newNotificacion(notificacion);
    }

    @PutMapping(path="/Update/{idNotificacion}")
    @PreAuthorize("hasAnyRole('Admin','Jefe','TrabajadorAdmin')")
    public ResponseEntity<Object>updateNotificacion(@PathVariable("idNotificacion") @NotNull Integer id, @RequestBody @Valid Notificacion notificacion){
        return this.notificacionService.updateNotificacion(id,notificacion);
    }

    @DeleteMapping(path="/Delete/{idNotificacion}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deleteTipoN(@PathVariable("idNotificacion") @NotNull Integer id){
        return this.notificacionService.deleteNotificacion(id);
    }
}

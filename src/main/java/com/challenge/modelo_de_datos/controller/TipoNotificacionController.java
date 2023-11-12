package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TipoNotificacion;
import com.challenge.modelo_de_datos.service.TipoNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/TipoNotificacion")
public class TipoNotificacionController {
    private final TipoNotificacionService tipoNotificacionService;

    @Autowired
    public TipoNotificacionController(TipoNotificacionService tipoNotificacionService) {
        this.tipoNotificacionService = tipoNotificacionService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin','Usuario','Comprador')")
    public List<TipoNotificacion> getTiposN(){
        return this.tipoNotificacionService.getTiposNotificaciones();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> addTipoN(@RequestBody @Valid TipoNotificacion tipoN){
        return this.tipoNotificacionService.newTipoNotificacion(tipoN);
    }

    @PutMapping(path="/Update/{idTipoNotificacion}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object>updateTipoN(@PathVariable("idTipoNotificacion") @NotNull Integer id, @RequestBody @Valid TipoNotificacion tipoN){
        return this.tipoNotificacionService.updateTipoNotificacion(id,tipoN);
    }

    @DeleteMapping(path="/Delete/{idTipoNotificacion}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deleteTipoN(@PathVariable("idTipoNotificacion") @NotNull Integer id){
        return this.tipoNotificacionService.deleteTipoNotificacion(id);
    }
}
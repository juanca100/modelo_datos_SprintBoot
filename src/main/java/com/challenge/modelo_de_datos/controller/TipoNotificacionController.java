package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TipoNotificacion;
import com.challenge.modelo_de_datos.service.TipoNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<TipoNotificacion> getTiposN(){
        return this.tipoNotificacionService.getTiposNotificaciones();
    }

    @PostMapping(path="/Create")
    public ResponseEntity<Object> addTipoN(@RequestBody @Valid TipoNotificacion tipoN){
        return this.tipoNotificacionService.newTipoNotificacion(tipoN);
    }

    @PutMapping(path="/Update/{idTipoNotificacion}")
    public ResponseEntity<Object>updateTipoN(@PathVariable("idTipoNotificacion") Integer id,@RequestBody @Valid TipoNotificacion tipoN){
        return this.tipoNotificacionService.updateTipoNotificacion(id,tipoN);
    }

    @DeleteMapping(path="/Delete/{idTipoNotificacion}")
    public ResponseEntity<Object> deleteTipoN(@PathVariable("idTipoNotificacion") Integer id){
        return this.tipoNotificacionService.deleteTipoNotificacion(id);
    }
}
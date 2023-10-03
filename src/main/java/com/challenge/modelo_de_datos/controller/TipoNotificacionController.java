package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.TipoNotificacion;
import com.challenge.modelo_de_datos.service.TipoNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "TiposNotificaciones")
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

    @PostMapping
    public ResponseEntity<Object> addTipoN(@RequestBody TipoNotificacion tipoN){
        return this.tipoNotificacionService.newTipoNotificacion(tipoN);
    }

    @PutMapping
    public ResponseEntity<Object>updateTipoN(@RequestBody TipoNotificacion tipoN){
        return this.tipoNotificacionService.updateTipoNotificacion(tipoN);
    }

    @DeleteMapping(path="{idTipoN}")
    public ResponseEntity<Object> deleteTipoN(@PathVariable("idTipoN") Integer id){
        return this.tipoNotificacionService.deleteTipoNotificacion(id);
    }
}





package com.challenge.modelo_de_datos.controller;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;

@ControllerAdvice
public class ExcepcionesController {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> ViolacionIntegridadDatos(DataIntegrityViolationException ex) {
        HashMap<String, Object> datos = new HashMap<>();
        datos.put("error",true);
        datos.put("message", " Error de integridad de datos: " + ex.getMessage());
        return new ResponseEntity<>(
                datos,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> ExcepcionesGlobales(Exception ex) {
        HashMap<String, Object> datos = new HashMap<>();
        datos.put("error",true);
        datos.put("message", "Error: " + ex.getMessage());
        return new ResponseEntity<>(
                datos,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}


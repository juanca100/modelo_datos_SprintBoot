package com.challenge.modelo_de_datos.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;

@ControllerAdvice
public class ExcepcionesController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        HashMap<String, Object> datos = new HashMap<>();
        datos.put("error",true);
        datos.put("message", " Ocurrió un error inesperado: " + ex.getMessage());
        return new ResponseEntity<>(
                datos,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // Agregar más métodos @ExceptionHandler para manejar excepciones específicas si es necesario.
}


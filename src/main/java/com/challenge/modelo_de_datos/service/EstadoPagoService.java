package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.EstadoPago;
import com.challenge.modelo_de_datos.repository.EstadoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class EstadoPagoService {
    private final EstadoPagoRepository estadoPagoRepository;

    @Autowired
    public EstadoPagoService(EstadoPagoRepository estadoPagoRepository) {
        this.estadoPagoRepository = estadoPagoRepository;
    }

    public List<EstadoPago> getEstadosPago() {
        return estadoPagoRepository.findAll();
    }

    public ResponseEntity<Object> newEstadoPago(EstadoPago estadoPago) {
        HashMap<String, Object> datos = new HashMap<>();
        Integer id=estadoPago.getIdEstadoPago();
        if (id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }
        if(estadoPago.getEstadoPago()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if (estadoPago.getEstadoPago().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (estadoPago.getEstadoPago().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        datos.put("message", "Se guardó con éxito");
        estadoPagoRepository.save(estadoPago);
        datos.put("data", estadoPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateEstadoPago(Integer id,EstadoPago estadoPago) {
        HashMap<String, Object> datos = new HashMap<>();
        if(estadoPago.getEstadoPago()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(!estadoPagoRepository.existsById(id)){
            datos.put("error", true);
            datos.put("message","El id proporcionado es erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        estadoPago.setIdEstadoPago(id);
        if (estadoPago.getEstadoPago().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (estadoPago.getEstadoPago().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        datos.put("message", "Se actualizo con éxito");
        estadoPagoRepository.save(estadoPago);
        datos.put("data", estadoPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteEstadoPago(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        if (!estadoPagoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe el EstadoPago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        estadoPagoRepository.deleteById(id);
        datos.put("message", "EstadoPago eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TipoPago;
import com.challenge.modelo_de_datos.repository.TipoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class TipoPagoService {
    private final TipoPagoRepository tipoPagoRepository;

    @Autowired
    public TipoPagoService(TipoPagoRepository tipoPagoRepository) {
        this.tipoPagoRepository = tipoPagoRepository;
    }

    public List<TipoPago> getTiposPago() {
        return tipoPagoRepository.findAll();
    }

    public ResponseEntity<Object> newTipoPago(TipoPago tipoPago) {
        HashMap<String, Object> datos = new HashMap<>();
        Integer id=tipoPago.getIdTipoPago();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(tipoPago.getTipoPago()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if (tipoPago.getTipoPago().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (tipoPago.getTipoPago().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        datos.put("message", "Se guardó con éxito");
        tipoPagoRepository.save(tipoPago);
        datos.put("data", tipoPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTipoPago(Integer id, TipoPago tipoPago) {
        HashMap<String, Object> datos = new HashMap<>();
        if(tipoPago.getTipoPago()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(!tipoPagoRepository.existsById(id)){
            datos.put("error", true);
            datos.put("message","El id proporcionado es erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        tipoPago.setIdTipoPago(id);
        if (tipoPago.getTipoPago().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (tipoPago.getTipoPago().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        datos.put("message", "Se actualizo con éxito");
        tipoPagoRepository.save(tipoPago);
        datos.put("data", tipoPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteTipoPago(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        if (!tipoPagoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe el TipoPago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        tipoPagoRepository.deleteById(id);
        datos.put("message", "TipoPago eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

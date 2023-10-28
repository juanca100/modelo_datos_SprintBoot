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
        // Aquí puedes realizar las validaciones necesarias antes de guardar el TipoPago
        TipoPago nuevoTipoPago = tipoPagoRepository.save(tipoPago);
        datos.put("message", "Se guardó con éxito");
        datos.put("data", nuevoTipoPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTipoPago(Integer id,TipoPago tipoPago) {
        HashMap<String, Object> datos = new HashMap<>();
        // Aquí puedes realizar las validaciones necesarias antes de actualizar el TipoPago
        if (!tipoPagoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe el TipoPago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        tipoPago.setIdTipoPago(id);
        TipoPago updatedTipoPago = tipoPagoRepository.save(tipoPago);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", updatedTipoPago);
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

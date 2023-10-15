package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TransaccionPago;
import com.challenge.modelo_de_datos.repository.TransaccionPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TransaccionPagoService {
    private final TransaccionPagoRepository transaccionPagoRepository;

    @Autowired
    public TransaccionPagoService(TransaccionPagoRepository transaccionPagoRepository) {
        this.transaccionPagoRepository = transaccionPagoRepository;
    }

    public List<TransaccionPago> getTransaccionesPago() {
        return transaccionPagoRepository.findAll();
    }

    public ResponseEntity<Object> newTransaccionPago(TransaccionPago transaccionPago) {
        HashMap<String, Object> datos = new HashMap<>();
        // Aquí puedes realizar las validaciones necesarias antes de guardar la TransaccionPago
        TransaccionPago nuevaTransaccionPago = transaccionPagoRepository.save(transaccionPago);
        datos.put("message", "Se guardó con éxito");
        datos.put("data", nuevaTransaccionPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTransaccionPago(TransaccionPago transaccionPago) {
        HashMap<String, Object> datos = new HashMap<>();
        // Aquí puedes realizar las validaciones necesarias antes de actualizar la TransaccionPago
        TransaccionPago updatedTransaccionPago = transaccionPagoRepository.save(transaccionPago);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", updatedTransaccionPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteTransaccionPago(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        if (!transaccionPagoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe la TransaccionPago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        transaccionPagoRepository.deleteById(id);
        datos.put("message", "TransaccionPago eliminada");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

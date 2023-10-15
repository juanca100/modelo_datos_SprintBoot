package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Transaccion_pago;
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

    public List<Transaccion_pago> getTransaccionesPago() {
        return transaccionPagoRepository.findAll();
    }

    public ResponseEntity<Object> newTransaccionPago(Transaccion_pago transaccionPago) {
        HashMap<String, Object> datos = new HashMap<>();
        // Aquí puedes realizar las validaciones necesarias antes de guardar la Transaccion_pago
        Transaccion_pago nuevaTransaccionPago = transaccionPagoRepository.save(transaccionPago);
        datos.put("message", "Se guardó con éxito");
        datos.put("data", nuevaTransaccionPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTransaccionPago(Transaccion_pago transaccionPago) {
        HashMap<String, Object> datos = new HashMap<>();
        // Aquí puedes realizar las validaciones necesarias antes de actualizar la Transaccion_pago
        Transaccion_pago updatedTransaccionPago = transaccionPagoRepository.save(transaccionPago);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", updatedTransaccionPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteTransaccionPago(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        if (!transaccionPagoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe la Transaccion_pago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        transaccionPagoRepository.deleteById(id);
        datos.put("message", "Transaccion_pago eliminada");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

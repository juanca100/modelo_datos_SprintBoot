package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Estado_pago;
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

    public List<Estado_pago> getEstadosPago() {
        return estadoPagoRepository.findAll();
    }

    public ResponseEntity<Object> newEstadoPago(Estado_pago estado_pago) {
        HashMap<String, Object> datos = new HashMap<>();
        // Aquí puedes realizar las validaciones necesarias antes de guardar el Estado_pago
        Estado_pago nuevoEstadoPago = estadoPagoRepository.save(estado_pago);
        datos.put("message", "Se guardó con éxito");
        datos.put("data", nuevoEstadoPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateEstadoPago(Estado_pago estado_pago) {
        HashMap<String, Object> datos = new HashMap<>();
        // Aquí puedes realizar las validaciones necesarias antes de actualizar el Estado_pago
        Estado_pago updatedEstadoPago = estadoPagoRepository.save(estado_pago);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", updatedEstadoPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteEstadoPago(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        if (!estadoPagoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe el Estado_pago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        estadoPagoRepository.deleteById(id);
        datos.put("message", "Estado_pago eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

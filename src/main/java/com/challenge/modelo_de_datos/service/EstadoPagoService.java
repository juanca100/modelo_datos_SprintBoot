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

    public ResponseEntity<Object> newEstadoPago(EstadoPago estado_pago) {
        HashMap<String, Object> datos = new HashMap<>();
        Integer id=estado_pago.getIdEstadoPago();
        if (id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(datos, HttpStatus.CREATED);

        }
        EstadoPago nuevoEstadoPago = estadoPagoRepository.save(estado_pago);
        datos.put("message", "Se guardó con éxito");
        datos.put("data", nuevoEstadoPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateEstadoPago(Integer id,EstadoPago estado_pago) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existePagoEstado=estadoPagoRepository.existsById(id);
        if(!existePagoEstado){
            datos.put("error", true);
            datos.put("message", "No existe el EstadoPago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        estado_pago.setIdEstadoPago(id);
        EstadoPago updatedEstadoPago = estadoPagoRepository.save(estado_pago);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", updatedEstadoPago);
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

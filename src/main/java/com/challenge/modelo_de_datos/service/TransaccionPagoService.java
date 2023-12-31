package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TransaccionPago;
import com.challenge.modelo_de_datos.repository.EstadoPagoRepository;
import com.challenge.modelo_de_datos.repository.TipoPagoRepository;
import com.challenge.modelo_de_datos.repository.TransaccionPagoRepository;
import com.challenge.modelo_de_datos.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TransaccionPagoService {
    private final TransaccionPagoRepository transaccionPagoRepository;
    private final TransaccionRepository transaccionRepository;
    private final EstadoPagoRepository estadoPagoRepository;
    private final TipoPagoRepository tipoPagoRepository;

    @Autowired
    public TransaccionPagoService(
            TransaccionPagoRepository transaccionPagoRepository,
            TransaccionRepository transaccionRepository,
            EstadoPagoRepository estadoPagoRepository,
            TipoPagoRepository tipoPagoRepository
    ) {
        this.transaccionPagoRepository = transaccionPagoRepository;
        this.transaccionRepository = transaccionRepository;
        this.estadoPagoRepository = estadoPagoRepository;
        this.tipoPagoRepository = tipoPagoRepository;
    }

    public List<TransaccionPago> getTransaccionesPago() {
        return transaccionPagoRepository.findAll();
    }

    public ResponseEntity<Object> newTransaccionPago(TransaccionPago transaccionPago) {
        HashMap<String, Object> datos = new HashMap<>();
        datos.put("message", "Se guardó con éxito");
        Integer id=transaccionPago.getIdTransaccionPago();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validación: Campos requeridos (id_transaccion, monto_total, descripcion, id_estado_pago, id_tipo_pago)
        if ((transaccionPago.getTransaccion()==null)||((Float)transaccionPago.getMonto_total() == 0) || (transaccionPago.getDescripcion() == null)
                || (transaccionPago.getEstadoPago() == null) || (transaccionPago.getTipoPago() == null)) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos obligatorios");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        if (transaccionPago.getDescripcion().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        if (transaccionPago.getDescripcion().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        if(transaccionPago.getMonto_total()<0){
            datos.put("error", true);
            datos.put("message", "Las cantidades numericas deben ser mayores a 0");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Realiza otras validaciones, como verificar que las referencias (id_transaccion, id_estado_pago, id_tipo_pago) existen en las tablas correspondientes
        if (!transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())) {
            datos.put("error", true);
            datos.put("message", "La Transaccion con ese ID no existe");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        if (!estadoPagoRepository.existsById(transaccionPago.getIdTransaccionPago())) {
            datos.put("error", true);
            datos.put("message", "El EstadoPago con ese ID no existe");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        if (!tipoPagoRepository.existsById(transaccionPago.getTipoPago().getIdTipoPago())) {
            datos.put("error", true);
            datos.put("message", "El TipoPago con ese ID no existe");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        // Guardar la TransaccionPago si todas las validaciones pasan
        transaccionPagoRepository.save(transaccionPago);
        datos.put("data", transaccionPago);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTransaccionPago(Integer id, TransaccionPago transaccionPago) {
        HashMap<String, Object> datos = new HashMap();

        // Validación: Campos requeridos (id_transaccion, monto_total, descripcion, id_estado_pago, id_tipo_pago)
        if (transaccionPago.getTransaccion() == null || transaccionPago.getMonto_total() == 0 || transaccionPago.getDescripcion() == null
                || transaccionPago.getEstadoPago() == null || transaccionPago.getTipoPago() == null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos obligatorios");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        // Validación: Existe la TransaccionPago con el ID proporcionado
        if (!transaccionPagoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe la TransaccionPago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
        transaccionPago.setIdTransaccionPago(id);

        if (transaccionPago.getDescripcion().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        if (transaccionPago.getDescripcion().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        if(transaccionPago.getMonto_total()<0){
            datos.put("error", true);
            datos.put("message", "Las cantidades numericas deben ser mayores a 0");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Realiza otras validaciones, como verificar que las referencias (id_transaccion, id_estado_pago, id_tipo_pago) existen en las tablas correspondientes

        if (!transaccionRepository.existsById(transaccionPago.getIdTransaccionPago())) {
            datos.put("error", true);
            datos.put("message", "La Transaccion con ese ID no existe");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        if (!estadoPagoRepository.existsById(transaccionPago.getEstadoPago().getIdEstadoPago())) {
            datos.put("error", true);
            datos.put("message", "El EstadoPago con ese ID no existe");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        if (!tipoPagoRepository.existsById(transaccionPago.getTipoPago().getIdTipoPago())){
            datos.put("error", true);
            datos.put("message", "El TipoPago con ese ID no existe");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        // Actualizar la TransaccionPago si todas las validaciones pasan
        transaccionPagoRepository.save(transaccionPago);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", transaccionPago);
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteTransaccionPago(Integer id) {
        HashMap<String, Object> datos = new HashMap();

        // Validación: Existe la TransaccionPago con el ID proporcionado
        if (!transaccionPagoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe la TransaccionPago con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }

        // Eliminar la TransaccionPago si la validación pasa
        transaccionPagoRepository.deleteById(id);
        datos.put("message", "TransaccionPago eliminada");
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }
}

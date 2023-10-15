package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Transaccion_producto;
import com.challenge.modelo_de_datos.repository.TransaccionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TransaccionProductoService {
    private final TransaccionProductoRepository transaccionProductoRepository;

    @Autowired
    public TransaccionProductoService(TransaccionProductoRepository transaccionProductoRepository) {
        this.transaccionProductoRepository = transaccionProductoRepository;
    }

    public List<Transaccion_producto> getTransaccionesProducto() {
        return transaccionProductoRepository.findAll();
    }

    public ResponseEntity<Object> newTransaccionProducto(Transaccion_producto transaccionProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        // Realiza validaciones necesarias antes de guardar la Transaccion_producto
        Transaccion_producto nuevaTransaccionProducto = transaccionProductoRepository.save(transaccionProducto);
        datos.put("message", "Se guardó con éxito");
        datos.put("data", nuevaTransaccionProducto);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTransaccionProducto(Transaccion_producto transaccionProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        // Realiza validaciones necesarias antes de actualizar la Transaccion_producto
        Transaccion_producto updatedTransaccionProducto = transaccionProductoRepository.save(transaccionProducto);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", updatedTransaccionProducto);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteTransaccionProducto(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        if (!transaccionProductoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe la Transaccion_producto con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        transaccionProductoRepository.deleteById(id);
        datos.put("message", "Transaccion_producto eliminada");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}


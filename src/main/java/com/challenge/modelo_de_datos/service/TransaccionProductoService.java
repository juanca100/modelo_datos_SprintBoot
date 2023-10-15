package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TransaccionProducto;
import com.challenge.modelo_de_datos.model.TransaccionProductoId;
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

    public List<TransaccionProducto> getTransaccionesProducto() {
        return transaccionProductoRepository.findAll();
    }

    public ResponseEntity<Object> newTransaccionProducto(TransaccionProducto transaccionProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        // Realiza validaciones necesarias antes de guardar la TransaccionProducto
        TransaccionProducto nuevaTransaccionProducto = transaccionProductoRepository.save(transaccionProducto);
        datos.put("message", "Se guardó con éxito");
        datos.put("data", nuevaTransaccionProducto);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTransaccionProducto(TransaccionProducto transaccionProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        // Realiza validaciones necesarias antes de actualizar la TransaccionProducto
        TransaccionProducto updatedTransaccionProducto = transaccionProductoRepository.save(transaccionProducto);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", updatedTransaccionProducto);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteTransaccionProducto(Integer idTransaccion,Integer idProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        TransaccionProductoId id=new TransaccionProductoId(idTransaccion,idProducto);
        if (!transaccionProductoRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe la TransaccionProducto con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        transaccionProductoRepository.deleteById(id);
        datos.put("message", "TransaccionProducto eliminada");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}


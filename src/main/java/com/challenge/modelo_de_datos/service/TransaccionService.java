package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Transaccion;
import com.challenge.modelo_de_datos.repository.CompradorRepository;
import com.challenge.modelo_de_datos.repository.TransaccionRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final VendedorRepository vendedorRepository;
    private final CompradorRepository compradorRepository;

    @Autowired
    public TransaccionService(
            TransaccionRepository transaccionRepository,
            VendedorRepository vendedorRepository,
            CompradorRepository compradorRepository
    ) {
        this.transaccionRepository = transaccionRepository;
        this.vendedorRepository = vendedorRepository;
        this.compradorRepository = compradorRepository;
    }

    public List<Transaccion> getTransacciones() {
        return this.transaccionRepository.findAll();
    }

    public ResponseEntity<Object> newTransaccion(Transaccion transaccion) {
        HashMap<String, Object> datos = new HashMap<>();

        // Validación: Comprador y Vendedor existen
        if (!compradorRepository.existsById(transaccion.getComprador().getIdComprador())) {
            datos.put("error", true);
            datos.put("message", "El comprador no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        if (!vendedorRepository.existsById(transaccion.getVendedor().getIdVendedor())) {
            datos.put("error", true);
            datos.put("message", "El vendedor no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Guardar la Transaccion si las validaciones pasan
        datos.put("message", "Se guardó con éxito");
        transaccionRepository.save(transaccion);
        datos.put("data", transaccion);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTransaccion(Integer id, Transaccion transaccion) {
        HashMap<String, Object> datos = new HashMap<>();

        // Validación: Comprador y Vendedor existen
        if (!compradorRepository.existsById(transaccion.getComprador().getIdComprador())) {
            datos.put("error", true);
            datos.put("message", "El comprador no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        if (!vendedorRepository.existsById(transaccion.getVendedor().getIdVendedor())) {
            datos.put("error", true);
            datos.put("message", "El vendedor no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Validación: Existe la Transaccion con el ID proporcionado
        if (!transaccionRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe la Transaccion con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Actualizar la Transaccion si las validaciones pasan
        transaccion.setIdTransaccion(id);
        datos.put("message", "Se actualizó con éxito");
        transaccionRepository.save(transaccion);
        datos.put("data", transaccion);
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteTransaccion(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();

        // Validación: Existe la Transaccion con el ID proporcionado
        if (!transaccionRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe la Transaccion con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Eliminar la Transaccion si la validación pasa
        transaccionRepository.deleteById(id);
        datos.put("message", "Transaccion eliminada");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

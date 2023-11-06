package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Inventario;
import com.challenge.modelo_de_datos.repository.InventarioRepository;
import com.challenge.modelo_de_datos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class InventarioService {
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public InventarioService(InventarioRepository inventarioRepository, ProductoRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
    }

    public List<Inventario> getInventarios() {
        return this.inventarioRepository.findAll();
    }

    public ResponseEntity<Object> newInventario(Inventario inventario) {
        HashMap<String, Object> datos = new HashMap<>();
        Integer id= inventario.getIdInventario();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        if(inventario.getProducto()==null||(Integer)inventario.getStockInicial()==0||(Integer)inventario.getStockMinimo()==0){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        if (inventario.getStockInicial()<=0){
            return createErrorResponse("El stock inicial del inventario debe ser mayor que cero.");

        }
        if (inventario.getStockMinimo()<=0){
            return createErrorResponse("el stock minimo del inventario debe ser mayor que cero.");

        }

        if (productoRepository.existsById(inventario.getProducto().getIdProducto())) {
            datos.put("message", "Se guardó con éxito");
            inventarioRepository.save(inventario);
            datos.put("data", inventario);
        } else {
            datos.put("message", "El producto no existe");
        }
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    private ResponseEntity<Object> createErrorResponse(String s) {
        HashMap<String, Object> datos = new HashMap<>();
        datos.put("message", s);
        return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<Object> updateInventario(Integer id, Inventario inventario) {
        HashMap<String, Object> datos = new HashMap<>();

        if(inventario.getProducto()==null||(Integer)inventario.getEntrada()==0||(Integer)inventario.getSalida()==0||(Integer)inventario.getStockInicial()==0||(Integer)inventario.getStockMinimo()==0){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        if (inventario.getStockInicial()<=0){
            return createErrorResponse("El stock inicial del inventario debe ser mayor que cero.");

        }
        if (inventario.getStockMinimo()<=0){
            return createErrorResponse("el stock minimo del inventario debe ser mayor que cero.");

        }

        if(!this.inventarioRepository.existsById(id)){
            datos.put("error", true);
            datos.put("message","El id proporcionado es erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        inventario.setIdInventario(id);
        if (this.productoRepository.existsById(inventario.getProducto().getIdProducto())) {
            datos.put("message", "Se guardó con éxito");
            inventarioRepository.save(inventario);
            datos.put("data", inventario);
        } else {
            datos.put("message", "El producto no existe");
        }
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteInventario(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();

        if (!inventarioRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe el inventario con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        inventarioRepository.deleteById(id);
        datos.put("message", "Inventario eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

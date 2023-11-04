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
        boolean existeProducto = this.productoRepository.existsById(inventario.getProducto().getIdProducto());
        int id= inventario.getIdInventario();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if (inventario.getEntrada()<=0){
            return createErrorResponse("La entrada del inventario debe ser mayor que cero.");
        }
        if (inventario.getStockInicial()<=0){
            return createErrorResponse("El stock inicial del inventario debe ser mayor que cero.");

        }
        if (inventario.getStockMinimo()<=0){
            return createErrorResponse("el stock minimo del inventario debe ser mayor que cero.");

        }
        if (existeProducto) {
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
        boolean existeInventario = this.inventarioRepository.existsById(id);
        boolean existeProducto = this.productoRepository.existsById(inventario.getProducto().getIdProducto());

        if (existeInventario) {
            if (existeProducto) {
                inventario.setIdInventario(id);
                datos.put("message", "Se actualizó con éxito");
                inventarioRepository.save(inventario);
                datos.put("data", inventario);
                return new ResponseEntity<>(datos, HttpStatus.CREATED);
            } else {
                datos.put("message", "El ID de producto no existe");
                return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
            }
        } else {
            datos.put("message", "El inventario con ese ID no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Object> deleteInventario(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existeInventario = this.inventarioRepository.existsById(id);

        if (!existeInventario) {
            datos.put("error", true);
            datos.put("message", "No existe el inventario con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        inventarioRepository.deleteById(id);
        datos.put("message", "Inventario eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

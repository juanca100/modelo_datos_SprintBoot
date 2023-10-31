package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Producto;
import com.challenge.modelo_de_datos.repository.ProductoRepository;
import com.challenge.modelo_de_datos.repository.TipoProductoRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final VendedorRepository vendedorRepository;
    private final TipoProductoRepository tipoProductoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, VendedorRepository vendedorRepository, TipoProductoRepository tipoProductoRepository) {
        this.productoRepository = productoRepository;
        this.vendedorRepository = vendedorRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public List<Producto> getProductos() {
        return this.productoRepository.findAll();
    }

    public ResponseEntity<Object> newProducto(Producto producto) {
        HashMap<String, Object> datos = new HashMap<>();
        Integer id = producto.getIdProducto();
        if (id != 0) {
            datos.put("error", true);
            datos.put("message", "No enviar ID, este se genera automáticamente");
            return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        // Validación de datos obligatorios
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            return createErrorResponse("El nombre del producto es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (producto.getPrecio() <= 0.0f) {
            return createErrorResponse("El precio del producto debe ser mayor que cero.", HttpStatus.BAD_REQUEST);
        }

        // Validación de existencia de entidad relacionada
        if (!vendedorRepository.existsById(producto.getVendedor().getIdVendedor())) {
            return createErrorResponse("El vendedor no existe en la base de datos.", HttpStatus.BAD_REQUEST);
        }

        if (!tipoProductoRepository.existsById(producto.getTipoProducto().getIdTipoProducto())) {
            return createErrorResponse("El tipo de producto no existe en la base de datos.", HttpStatus.BAD_REQUEST);
        }

        // Guarda el producto en la base de datos.
        productoRepository.save(producto);

        datos.put("message", "Se guardó con éxito");
        datos.put("data", producto);

        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateProducto(Integer id, Producto producto) {
        HashMap<String, Object> datos = new HashMap();
        boolean existeVendedor = this.productoRepository.existsById(producto.getVendedor().getIdVendedor());
        boolean existeProducto = this.productoRepository.existsById(id);

        if (existeProducto) {
            if (existeVendedor) {
                producto.setIdProducto(id);
                datos.put("message", "Se actualizó con éxito");
                productoRepository.save(producto);
                datos.put("data", producto);
                return new ResponseEntity<>(datos, HttpStatus.CREATED);
            } else {
                datos.put("message", "El vendedor no existe");
                return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
            }
        } else {
            datos.put("message", "El producto no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Object> deleteProducto(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existe = this.productoRepository.existsById(id);

        if (!existe) {
            datos.put("error", true);
            datos.put("message", "No existe el producto con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        productoRepository.deleteById(id);
        datos.put("message", "Producto eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }

    private ResponseEntity<Object> createErrorResponse(String message, HttpStatus status) {
        HashMap<String, Object> datos = new HashMap<>();
        datos.put("message", message);
        return new ResponseEntity<>(datos, status);
    }
}

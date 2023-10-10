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
    public List<Producto> getProductos(){
        return this.productoRepository.findAll();
    }
    public ResponseEntity<Object> newProducto(Producto producto){
        HashMap<String, Object> datos = new HashMap<>();
        boolean existeEstado = this.productoRepository.existsById(producto.getVendedor().getIdVendedor());
        if (existeEstado) {
            datos.put("message", "Se guardo con exito");
            productoRepository.save(producto);
            datos.put("data", producto);
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        } else {
            datos.put("message", "El producto no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
    }
    public ResponseEntity<Object> updateProducto (Producto producto){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeVendedor=this.productoRepository.existsById(producto.getVendedor().getIdVendedor());
        boolean existeProducto=this.productoRepository.existsById(producto.getIdProducto());
        if(existeProducto){
            if(existeVendedor){
                datos.put("message","Se actualizo con exito");
                productoRepository.save(producto);
                datos.put("data",producto);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El vendedor no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
        }
        else{
            datos.put("message","el producto no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
    }
    public ResponseEntity<Object> deleteProducto(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existe=this.productoRepository.existsById(id);
        if(!existe){
            datos.put("error",true);
            datos.put("message","No existe el producto con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        productoRepository.deleteById(id);
        datos.put("message","Producto eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}

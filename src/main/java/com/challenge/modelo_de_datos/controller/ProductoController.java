package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Producto;
import com.challenge.modelo_de_datos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "productos")
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    @GetMapping
    public List<Producto> getProductos(){
        return this.productoService.getProductos();
    }
    @PostMapping
    public ResponseEntity<Object> addProducto(@RequestBody Producto producto){return this.productoService.newProducto(producto);}

    @PutMapping
    public  ResponseEntity<Object> updateProducto(@RequestBody Producto producto){
        return this.productoService.updateProducto(producto);
    }
    @DeleteMapping(path="{idProducto}")
    public  ResponseEntity<Object> deleteProducto(@PathVariable("idProducto") Integer id){
        return  this.productoService.deleteProducto(id);
    }
}

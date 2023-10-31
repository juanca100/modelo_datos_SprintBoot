package com.challenge.modelo_de_datos.controller;

import com.challenge.modelo_de_datos.model.Producto;
import com.challenge.modelo_de_datos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Producto")
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
    @PostMapping(path="/Create")
    public ResponseEntity<Object> addProducto(@RequestBody  @Valid  Producto producto){return this.productoService.newProducto(producto);}

    @PutMapping(path="/Update/{idProducto}")
    public  ResponseEntity<Object> updateProducto(@PathVariable("idProducto") @NotNull Integer id, @RequestBody @Valid Producto producto){
        return this.productoService.updateProducto(id,producto);
    }
    @DeleteMapping(path="/Delete/{idProducto}")
    public  ResponseEntity<Object> deleteProducto(@PathVariable("idProducto")@NotNull Integer id){
        return  this.productoService.deleteProducto(id);
    }
}

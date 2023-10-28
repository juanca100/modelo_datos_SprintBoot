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
    public List<Inventario> getInventarios(){
        return this.inventarioRepository.findAll();
    }
    public ResponseEntity<Object> newInventario(Inventario inventario){
        HashMap<String , Object> datos = new HashMap<>();
        boolean existeProducto= this.productoRepository.existsById(inventario.getProducto().getIdProducto());
        if(existeProducto){
            datos.put("message", "se guardo con exito");
            inventarioRepository.save(inventario);
            datos.put("data", inventario);
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }else {
            datos.put("message", "El producto no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
    }
    public ResponseEntity<Object> updateInventario (Integer id,Inventario inventario) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeInventario=this.inventarioRepository.existsById(id);
        boolean existeProducto = this.productoRepository.existsById(inventario.getProducto().getIdProducto());
        if(existeInventario){
            if(existeProducto){
                datos.put("message","Se actualizo con exito");
                inventarioRepository.save(inventario);
                datos.put("data",inventario);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El id de producto no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
        else{
            datos.put("message","El inventario con ese id no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> deleteInventario(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeInventario=this.inventarioRepository.existsById(id);
        if(!existeInventario){
            datos.put("error",true);
            datos.put("message","No existe el inventario con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        inventarioRepository.deleteById(id);
        datos.put("message","Inventario eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }

}

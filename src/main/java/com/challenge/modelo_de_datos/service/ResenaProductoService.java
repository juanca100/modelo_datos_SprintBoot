package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Producto;
import com.challenge.modelo_de_datos.model.ResenaProducto;
import com.challenge.modelo_de_datos.model.Transaccion;
import com.challenge.modelo_de_datos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ResenaProductoService {

    private final ResenaProductoRepository resenaProductoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired

    public ResenaProductoService(ResenaProductoRepository resenaProductoRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.resenaProductoRepository = resenaProductoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ResenaProducto> getResenaProducto(){
        return this.resenaProductoRepository.findAll();
    }

    public ResponseEntity<Object> newResenaProducto(ResenaProducto resenaProducto) {
        HashMap<String,Object> datos= new HashMap<>();
        //boolean existeProducto=this.productoRepository.existsById(resenaProducto.getProducto().getIdProducto());
        //boolean existeUsuario=this.usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario());
        //if(existeProducto){
          //  if(existeUsuario){
                datos.put("message","Se guardo con exito");
                resenaProductoRepository.save(resenaProducto);
                datos.put("data",resenaProducto);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
           // }
            //else{
              //  datos.put("message","El usuario no existe");
               // return new ResponseEntity<>(
                 //       datos,
                   //     HttpStatus.CREATED
               // );
            //}
        //}
        //else{
          //  datos.put("message","El producto no existe");
            //return new ResponseEntity<>(
              //      datos,
                //    HttpStatus.CREATED
            //);
        //}
    }

    public ResponseEntity<Object> updateResenaProducto(ResenaProducto resenaProducto) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeProducto=this.productoRepository.existsById(resenaProducto.getProducto().getIdProducto());
        boolean existeUsuario=this.usuarioRepository.existsById(resenaProducto.getProducto().getIdProducto());
        if(existeProducto){
            if(existeUsuario){
                datos.put("message","Se actuaslizo con exito");
                resenaProductoRepository.save(resenaProducto);
                datos.put("data",resenaProducto);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El usuario no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
        }
        else{
            datos.put("message","El producto no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
    }

    public ResponseEntity<Object> deleteResenaProducto(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existe=this.resenaProductoRepository.existsById(id);
        if(!existe){
            datos.put("error",true);
            datos.put("message","No hay resenaProducto con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        resenaProductoRepository.deleteById(id);
        datos.put("message","resenaProducto eliminada");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }





}

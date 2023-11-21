package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.ResenaProducto;
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

    public List<ResenaProducto> getResenasProducto(){
        return this.resenaProductoRepository.findAll();
    }

    public ResponseEntity<Object> newResenaProducto(ResenaProducto resenaProducto) {
        HashMap<String,Object> datos= new HashMap<>();
        Integer id = resenaProducto.getIdResenaProducto();

        if (id != 0) {
            datos.put("error", true);
            datos.put("message", "No enviar ID, este se genera automáticamente");
           return new ResponseEntity<>(datos, HttpStatus.BAD_REQUEST);
        }

        if(resenaProducto.getDescripcion()==null||resenaProducto.getProducto()==null||resenaProducto.getUsuario()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        if(resenaProducto.getDescripcion().isBlank()||resenaProducto.getDescripcion()==null){
            return createErrorResponse("El nombre del producto es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        if (resenaProducto.getCalificacionProducto() < 0.0f) {
            return createErrorResponse("LA CALIFICACION DEBE SER NUMERICA POSITIVA", HttpStatus.BAD_REQUEST);
        }

        if(!usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())){
            return createErrorResponse("El usuario no existe, ingrese un ID valido",HttpStatus.BAD_REQUEST);
        }

        if(!productoRepository.existsById(resenaProducto.getProducto().getIdProducto())){
            return createErrorResponse("El producto no existe, ingrese un ID valido",HttpStatus.BAD_REQUEST);
        }

        if (resenaProducto.getDescripcion().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        resenaProductoRepository.save(resenaProducto);
        datos.put("message","SE GUARDO RESEÑA CON EXITO");
        datos.put("data",resenaProducto);

        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateResenaProducto(Integer id,ResenaProducto resenaProducto) {
        HashMap<String,Object> datos= new HashMap<>();

        if(resenaProducto.getDescripcion()==null||resenaProducto.getProducto()==null||resenaProducto.getUsuario()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        if(!resenaProductoRepository.existsById(id)){
            datos.put("error", true);
            datos.put("message","La reseña no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        if(resenaProducto.getDescripcion().isBlank()||resenaProducto.getDescripcion()==null){
            return createErrorResponse("El nombre del producto es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        if (resenaProducto.getCalificacionProducto() < 0.0f) {
            return createErrorResponse("LA CALIFICACION DEBE SER NUMERICA POSITIVA", HttpStatus.BAD_REQUEST);
        }

        if(!usuarioRepository.existsById(resenaProducto.getUsuario().getIdUsuario())){
            return createErrorResponse("El usuario no existe, ingrese un ID valido",HttpStatus.BAD_REQUEST);
        }

        if(!productoRepository.existsById(resenaProducto.getProducto().getIdProducto())){
            return createErrorResponse("El producto no existe, ingrese un ID valido",HttpStatus.BAD_REQUEST);
        }

        if (resenaProducto.getDescripcion().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        resenaProducto.setIdResenaProducto(id);
        resenaProductoRepository.save(resenaProducto);
        datos.put("message","SE GUARDO RESEÑA CON EXITO");
        datos.put("data",resenaProducto);

        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteResenaProducto(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeRP=this.resenaProductoRepository.existsById(id);
        if(!existeRP){
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

    private ResponseEntity<Object> createErrorResponse(String message, HttpStatus status) {
        HashMap<String, Object> datos = new HashMap<>();
        datos.put("message", message);
        return new ResponseEntity<>(datos, status);
    }
}

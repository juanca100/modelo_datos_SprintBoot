package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.ResenaTienda;
import com.challenge.modelo_de_datos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
@Service
public class ResenaTiendaService {


    private final ResenaTiendaRepository resenaTiendaRepository;
    private final VendedorRepository vendedorRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ResenaTiendaService(ResenaTiendaRepository resenaTiendaRepository, VendedorRepository vendedorRepository, UsuarioRepository usuarioRepository) {
            this.resenaTiendaRepository = resenaTiendaRepository;
            this.vendedorRepository = vendedorRepository;
            this.usuarioRepository = usuarioRepository;
    }

    public List<ResenaTienda> getResenasTienda(){
        return this.resenaTiendaRepository.findAll();
    }

    public ResponseEntity<Object> newResenaTienda(ResenaTienda resenaTienda) {
        HashMap<String,Object> datos= new HashMap<>();
        Integer id= resenaTienda.getIdResenaTienda();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        //VALIDAR QUE LA CALIFICACION DE LA TIENDA SEAN NUMEROS POSITIVOS
        if(resenaTienda.getCalificacionTienda()<0){
            return createErrorResponse("El precio del producto debe ser mayor que cero.", HttpStatus.BAD_REQUEST);
        }

        //VALIDAR QUE EL VENVEDOR EXISTA
        if(!vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor())){
            return createErrorResponse("El vendedor no existe, ingrese un ID valido",HttpStatus.BAD_REQUEST);
        }

        //VALIDAR QUE EL USUARIO EXISTA
        if(!usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario())){
            return createErrorResponse("El usuario no existe, ingrese un ID valido",HttpStatus.BAD_REQUEST);
        }else{

            if (resenaTienda.getDescripcion().matches("\\d+")) {
                datos.put("error", true);
                datos.put("message", "Las campos de caracteres no deben ser numeros");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }

        }

        if(resenaTienda.getDescripcion().isBlank() || resenaTienda.getDescripcion()==null){
            return createErrorResponse("El campo descripcion no puede ser nulo", HttpStatus.BAD_REQUEST);
        }

        resenaTiendaRepository.save(resenaTienda);
        datos.put("message","SE GUARDO RESEÑA CON EXITO");
        datos.put("data",resenaTienda);

        return new ResponseEntity<>(datos, HttpStatus.CREATED);

    }

    public ResponseEntity<Object> updateResenaTienda(Integer id,ResenaTienda resenaTienda) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeVendedor=this.vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor());
        boolean existeUsuario=this.usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario());
        boolean existeRT=this.resenaTiendaRepository.existsById(id);

        if (existeRT){
            if (existeUsuario){
                if(existeVendedor){

                    if (resenaTienda.getDescripcion().matches("\\d+")) {
                        datos.put("error", true);
                        datos.put("message", "Las campos de caracteres no deben ser numeros");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );


                    }else{

                        datos.put("message","SE ACTUALIZO CON EXITO");
                        resenaTiendaRepository.save(resenaTienda);
                        datos.put("data",resenaTienda);
                        return new ResponseEntity<>(datos, HttpStatus.CREATED);
                    }



                }else{
                    datos.put("message", "El veendedor no existe");
                    return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
                }

            }else{
                datos.put("message", "El usuario no existe");
                return new ResponseEntity<>(datos, HttpStatus.CONFLICT);

            }

        }else{
            datos.put("message", "La reseña_tienda no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);

        }

    }

    public ResponseEntity<Object> deleteResenaTieneda(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeRT=this.resenaTiendaRepository.existsById(id);
        if(!existeRT){
            datos.put("error",true);
            datos.put("message","No hay resenaTienda con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        resenaTiendaRepository.deleteById(id);
        datos.put("message","resenaTienda eliminada");
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

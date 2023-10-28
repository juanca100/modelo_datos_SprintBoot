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
        boolean existeVendedor=this.vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor());
        boolean existeUsuario=this.usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario());
        if(existeVendedor){
            if(existeUsuario){
                datos.put("message","Se guardo con exito");
                resenaTiendaRepository.save(resenaTienda);
                datos.put("data",resenaTienda);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El usuario no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
        else{
            datos.put("message","El vendedor no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> updateResenaTienda(Integer id,ResenaTienda resenaTienda) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeVendedor=this.vendedorRepository.existsById(resenaTienda.getVendedor().getIdVendedor());
        boolean existeUsuario=this.usuarioRepository.existsById(resenaTienda.getUsuario().getIdUsuario());
        boolean existeRT=this.resenaTiendaRepository.existsById(id);
        if(existeVendedor){
            if(existeUsuario){
                if(!existeRT){
                    datos.put("error",true);
                    datos.put("message","No hay resenaTienda con ese id");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                resenaTienda.setIdResenaTienda(id);
                datos.put("message","Se actualizo con exito");
                resenaTiendaRepository.save(resenaTienda);
                datos.put("data",resenaTienda);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("error",true);
                datos.put("message","El usuario no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
        else{
            datos.put("error",true);
            datos.put("message","El vendedor no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
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
}

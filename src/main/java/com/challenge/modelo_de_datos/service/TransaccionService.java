package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Transaccion;
import com.challenge.modelo_de_datos.repository.CompradorRepository;
import com.challenge.modelo_de_datos.repository.TransaccionRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final VendedorRepository vendedorRepository;
    private final CompradorRepository compradorRepository;

    @Autowired
    public TransaccionService(TransaccionRepository transaccionRepository, VendedorRepository vendedorRepository, CompradorRepository compradorRepository) {
        this.transaccionRepository = transaccionRepository;
        this.vendedorRepository = vendedorRepository;
        this.compradorRepository = compradorRepository;
    }

    public List<Transaccion> getTransacciones(){
        return this.transaccionRepository.findAll();
    }

    public ResponseEntity<Object> newTransaccion(Transaccion transaccion) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeComprador=this.compradorRepository.existsById(transaccion.getComprador().getIdComprador());
        boolean existeVendedor=this.vendedorRepository.existsById(transaccion.getVendedor().getIdVendedor());
        if(existeComprador){
            if(existeVendedor){
                datos.put("message","Se guardo con exito");
                transaccionRepository.save(transaccion);
                datos.put("data",transaccion);
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
            datos.put("message","El comprador no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
    }

    public ResponseEntity<Object> updateTransaccion(Integer id,Transaccion transaccion) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeComprador=this.compradorRepository.existsById(transaccion.getComprador().getIdComprador());
        boolean existeVendedor=this.vendedorRepository.existsById(transaccion.getVendedor().getIdVendedor());
        boolean existeTransaccion=this.transaccionRepository.existsById(id);
        if(existeComprador){
            if(existeVendedor){
                if(!existeTransaccion){
                    datos.put("error",true);
                    datos.put("message","No hay transaccion con ese id");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                transaccion.setIdTransaccion(id);
                datos.put("message","Se actualizo con exito");
                transaccionRepository.save(transaccion);
                datos.put("data",transaccion);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","El vendedor no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
        else{
            datos.put("message","El comprador no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> deleteTransaccion(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeTransaccion=this.transaccionRepository.existsById(id);
        if(!existeTransaccion){
            datos.put("error",true);
            datos.put("message","No hay transaccion con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        transaccionRepository.deleteById(id);
        datos.put("message","Transaccion eliminada");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}


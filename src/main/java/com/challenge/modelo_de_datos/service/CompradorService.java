package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Comprador;
import com.challenge.modelo_de_datos.repository.CiudadRepository;
import com.challenge.modelo_de_datos.repository.CompradorRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CompradorService {
    private final CompradorRepository compradorRepository;
    private final CiudadRepository ciudadRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CompradorService(CompradorRepository compradorRepository, CiudadRepository ciudadRepository, UsuarioRepository usuarioRepository) {
        this.compradorRepository = compradorRepository;
        this.ciudadRepository = ciudadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Comprador> getCompradores() {
        return this.compradorRepository.findAll();
    }

    public ResponseEntity<Object> newComprador(Comprador comprador) {
        HashMap<String, Object> datos = new HashMap<>();
        Integer id=comprador.getIdComprador();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(comprador.getCiudad()==null||comprador.getUsuario()==null||comprador.getDireccion()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if (comprador.getDireccion().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (comprador.getDireccion().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if(!usuarioRepository.existsById(comprador.getUsuario().getIdUsuario())){
            datos.put("error", true);
            datos.put("message", "El usuario no existe, ID erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
        if(!ciudadRepository.existsById(comprador.getCiudad().getIdCiudad())){
            datos.put("error", true);
            datos.put("message", "La Ciudad no existe, ID erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
        datos.put("message", "Se guardó con éxito");
        compradorRepository.save(comprador);
        datos.put("data", comprador);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateComprador(Integer id, Comprador comprador) {
        HashMap<String, Object> datos = new HashMap<>();
        if(comprador.getCiudad()==null||comprador.getUsuario()==null||comprador.getDireccion()==null){
            datos.put("error",true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(!compradorRepository.existsById(id)){
            datos.put("error", true);
            datos.put("message","El id del comprador proporcionado es erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        comprador.setIdComprador(id);
        if (comprador.getDireccion().isBlank()) {
            datos.put("error", true);
            datos.put("message", "Los campos de caracteres no deben estar vacios");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if (comprador.getDireccion().matches("\\d+")) {
            datos.put("error", true);
            datos.put("message", "Las campos de caracteres no deben ser numeros");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        if(!usuarioRepository.existsById(comprador.getUsuario().getIdUsuario())){
            datos.put("error", true);
            datos.put("message", "El usuario no existe, ID erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
        if(!ciudadRepository.existsById(comprador.getCiudad().getIdCiudad())){
            datos.put("error", true);
            datos.put("message", "La Ciudad no existe, ID erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
        datos.put("message", "Se guardó con éxito");
        compradorRepository.save(comprador);
        datos.put("data", comprador);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteComprador(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();

        if (!compradorRepository.existsById(id)) {
            datos.put("error", true);
            datos.put("message", "No existe el comprador con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        compradorRepository.deleteById(id);
        datos.put("message", "Comprador eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

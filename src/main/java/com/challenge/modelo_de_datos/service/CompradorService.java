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
        boolean existeUsuario = this.usuarioRepository.existsById(comprador.getUsuario().getIdUsuario());
        boolean existeCiudad = this.ciudadRepository.existsById(comprador.getCiudad().getIdCiudad());

        if (existeUsuario) {
            if (existeCiudad) {
                datos.put("message", "Se guardó con éxito");
                compradorRepository.save(comprador);
                datos.put("data", comprador);
                return new ResponseEntity<>(datos, HttpStatus.CREATED);
            } else {
                datos.put("message", "La ciudad no existe");
                return new ResponseEntity<>(datos, HttpStatus.CREATED);
            }
        } else {
            datos.put("message", "El usuario no existe");
            return new ResponseEntity<>(datos, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Object> updateComprador(Integer id, Comprador comprador) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existeUsuario = this.usuarioRepository.existsById(comprador.getUsuario().getIdUsuario());
        boolean existeCiudad = this.ciudadRepository.existsById(comprador.getCiudad().getIdCiudad());
        boolean existeComprador = this.compradorRepository.existsById(id);

        if (existeUsuario) {
            if (existeCiudad) {
                if (!existeComprador) {
                    datos.put("error", true);
                    datos.put("message", "No existe el comprador con ese ID");
                    return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
                }
                comprador.setIdComprador(id);
                datos.put("message", "Se actualizó con éxito");
                compradorRepository.save(comprador);
                datos.put("data", comprador);
                return new ResponseEntity<>(datos, HttpStatus.CREATED);
            } else {
                datos.put("message", "La ciudad no existe");
                return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
            }
        } else {
            datos.put("message", "El usuario no existe");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Object> deleteComprador(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existeComprador = this.compradorRepository.existsById(id);

        if (!existeComprador) {
            datos.put("error", true);
            datos.put("message", "No existe el comprador con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        compradorRepository.deleteById(id);
        datos.put("message", "Comprador eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

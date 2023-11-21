package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.*;
import com.challenge.modelo_de_datos.repository.RolRepository;
import com.challenge.modelo_de_datos.repository.RolUsuarioRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RolUsuarioService {
    private final RolUsuarioRepository rolUsuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RolUsuarioService(RolUsuarioRepository rolUsuarioRepository, RolRepository rolRepository, UsuarioRepository usuarioRepository) {
        this.rolUsuarioRepository = rolUsuarioRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<RolUsuario> getRolesUsuario(){
        return this.rolUsuarioRepository.findAll();
    }

    public ResponseEntity<Object> newRolUsuario(RolUsuario rolUsuario) {
        HashMap<String, Object> datos = new HashMap<>();
        if(rolUsuario.getUsuario()==null||rolUsuario.getRol()==null){
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(datos,HttpStatus.CONFLICT);
        }
        RolUsuarioId id=new RolUsuarioId(rolUsuario.getRol().getIdRol(),rolUsuario.getUsuario().getIdUsuario());
        if (rolUsuarioRepository.existsById(id)) {
            datos.put("error",true);
            datos.put("message", "Esta asignacion ya se guardo en la base de datos");
            return new ResponseEntity<>(datos,HttpStatus.BAD_REQUEST);
        }
        if(!rolRepository.existsById(rolUsuario.getRol().getIdRol())){
            datos.put("error", true);
            datos.put("message","El rol no existe,ID erroneo");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        if(!usuarioRepository.existsById(rolUsuario.getUsuario().getIdUsuario())){
            datos.put("error", true);
            datos.put("message","El usuario no existe,ID erroneo");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        rolUsuarioRepository.save(rolUsuario);
        datos.put("message", "Se asigno con éxito");
        datos.put("data", rolUsuario);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteRolUsuario(Integer idRol,Integer idUsuario) {
        HashMap<String, Object> datos = new HashMap<>();
        RolUsuarioId Id=new RolUsuarioId(idRol,idUsuario);
        if(!rolUsuarioRepository.existsById(Id)){
            datos.put("error", true);
            datos.put("message", "No existe la relacion con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }
        rolUsuarioRepository.deleteById(Id);
        datos.put("message", "Asignacion eliminada");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

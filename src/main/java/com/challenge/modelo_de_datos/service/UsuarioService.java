package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios(){
        return this.usuarioRepository.findAll();
    }

    public ResponseEntity<Object> newUsuario(Usuario usuario) {
        HashMap<String,Object> datos= new HashMap<>();
        Integer id=usuario.getIdUsuario();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(usuario.getNombre()==null||usuario.getEmail()==null||usuario.getPassword()==null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla excepto el ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        else{
            if (usuario.getNombre().isBlank()||usuario.getEmail().isBlank()||usuario.getPassword().isBlank()) {
                datos.put("error", true);
                datos.put("message", "Los campos de texto no deben estar vacios");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
            else{
                if (usuario.getNombre().matches("\\d+")||usuario.getEmail().matches("\\d+")) {
                    datos.put("error", true);
                    datos.put("message", "Las campos de texto no deben ser numeros");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                else{
                    datos.put("message","Se guardo con exito");
                    usuarioRepository.save(usuario);
                    datos.put("data",usuario);
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CREATED
                    );
                }
            }
        }
    }

    public ResponseEntity<Object> updateUsuario(Integer id,Usuario usuario) {
        HashMap<String,Object> datos= new HashMap<>();
        if(usuario.getNombre()==null||usuario.getEmail()==null||usuario.getPassword()==null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla excepto el ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        else{
            if(usuarioRepository.existsById(id)) {
                usuario.setIdUsuario(id);
                if (usuario.getNombre().isBlank()||usuario.getEmail().isBlank()||usuario.getPassword().isBlank()) {
                    datos.put("error", true);
                    datos.put("message", "Los campos de texto no deben estar vacios");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                else{
                    if (usuario.getNombre().matches("\\d+")||usuario.getEmail().matches("\\d+")) {
                        datos.put("error", true);
                        datos.put("message", "Las campos de texto no deben ser numeros");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );
                    }
                    else{
                        datos.put("message","Se actualizo el usuario");
                        usuarioRepository.save(usuario);
                        datos.put("data",usuario);
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CREATED
                        );
                    }
                }
            }
            else{
                datos.put("error",true);
                datos.put("message","El id proporcionado del Usuario es erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    public ResponseEntity<Object> deleteUsuario(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!usuarioRepository.existsById(id)){
            datos.put("error",true);
            datos.put("message","No existe el usuario con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        usuarioRepository.deleteById(id);
        datos.put("message","Usuario eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}
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
        datos.put("message","Se guardo con exito");
        usuarioRepository.save(usuario);
        datos.put("data",usuario);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateUsuario(Integer id,Usuario usuario) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeUsuario = this.usuarioRepository.existsById(id);
        if(!existeUsuario){
            datos.put("error",true);
            datos.put("message","No existe el usuario con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        usuario.setIdUsuario(id);
        datos.put("message","Se actualizo el usuario");
        usuarioRepository.save(usuario);
        datos.put("data",usuario);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteUsuario(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeUsuario=this.usuarioRepository.existsById(id);
        if(!existeUsuario){
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

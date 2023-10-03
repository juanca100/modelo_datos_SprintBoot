package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Comprador;
import com.challenge.modelo_de_datos.repository.CiudadRepository;
import com.challenge.modelo_de_datos.repository.CompradorRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "compradores")
public class CompradorService{
        private final CompradorRepository CompradorRepository;
        private final CiudadRepository CiudadRepository;
        private final UsuarioRepository UsuarioRepository;

        @Autowired
        public CompradorService(CompradorRepository compradorRepository, CiudadRepository ciudadRepository, UsuarioRepository usuarioRepository) {
            CompradorRepository = compradorRepository;
            CiudadRepository = ciudadRepository;
            UsuarioRepository = usuarioRepository;
        }

        public List<Comprador> getCompradores()
        {
            return this.CompradorRepository.findAll();
        }

        public ResponseEntity<Object> newComprador(Comprador comprador) {
            HashMap<String,Object> datos= new HashMap<>();
            boolean existeUsuario=this.UsuarioRepository.existsById(comprador.getUsuario().getIdUsuario());
            boolean existeCiudad=this.CiudadRepository.existsById(comprador.getCiudad().getIdCiudad());
            if(existeUsuario){
                if(existeCiudad){
                    datos.put("message","Se guardo con exito");
                    CompradorRepository.save(comprador);
                    datos.put("data",comprador);
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CREATED
                    );
                }
                else{
                    datos.put("message","La ciudad no existe");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CREATED
                    );
                }
            }
            else{
                datos.put("message","El usuario no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
        }

        public ResponseEntity<Object> updateComprador(Comprador comprador) {
            HashMap<String,Object> datos= new HashMap<>();
            boolean existeUsuario=this.UsuarioRepository.existsById(comprador.getUsuario().getIdUsuario());
            boolean existeCiudad=this.CiudadRepository.existsById(comprador.getCiudad().getIdCiudad());
            if(existeUsuario){
                if(existeCiudad){
                    datos.put("message","Se guardo con exito");
                    CompradorRepository.save(comprador);
                    datos.put("data",comprador);
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CREATED
                    );
                }
                else{
                    datos.put("message","La ciudad no existe");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CREATED
                    );
                }
            }
            else{
                datos.put("message","El usuario no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
        }

        public ResponseEntity<Object> deleteComprados(Integer id){
            HashMap<String,Object> datos= new HashMap<>();
            boolean existe=this.CompradorRepository.existsById(id);
            if(!existe){
                datos.put("error",true);
                datos.put("message","No existe el comprador con ese id");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
            CompradorRepository.deleteById(id);
            datos.put("message","Comprador eliminado");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.ACCEPTED
            );
        }

}

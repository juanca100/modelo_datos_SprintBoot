package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Vendedor;
import com.challenge.modelo_de_datos.repository.CiudadRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "vendedores")
public class VendedorService {
    private final VendedorRepository VendedorRepository;
    private final CiudadRepository CiudadRepository;
    private final UsuarioRepository UsuarioRepository;

    @Autowired
    public VendedorService(VendedorRepository vendedorRepository, CiudadRepository ciudadRepository, UsuarioRepository usuarioRepository) {
        VendedorRepository = vendedorRepository;
        CiudadRepository = ciudadRepository;
        UsuarioRepository = usuarioRepository;
    }

    public List<Vendedor> getVendedores()
    {
        return this.VendedorRepository.findAll();
    }

    public ResponseEntity<Object> newVendedor(Vendedor vendedor) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeUsuario=this.UsuarioRepository.existsById(vendedor.getUsuario().getIdUsuario());
        boolean existeCiudad=this.CiudadRepository.existsById(vendedor.getCiudad().getIdCiudad());
        if(existeUsuario){
            if(existeCiudad){
                datos.put("message","Se guardo con exito");
                VendedorRepository.save(vendedor);
                datos.put("data",vendedor);
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

    public ResponseEntity<Object> updateVendedor(Integer id,Vendedor vendedor) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeUsuario=this.UsuarioRepository.existsById(vendedor.getUsuario().getIdUsuario());
        boolean existeCiudad=this.CiudadRepository.existsById(vendedor.getCiudad().getIdCiudad());
        boolean existeVendedor=this.VendedorRepository.existsById(id);
        if(existeUsuario){
            if(existeCiudad){
                if(!existeVendedor){
                    datos.put("error",true);
                    datos.put("message","No existe el tipo de producto con ese id");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                datos.put("message","Se actualizo con exito");
                VendedorRepository.save(vendedor);
                datos.put("data",vendedor);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );
            }
            else{
                datos.put("message","La ciudad no existe");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
        else{
            datos.put("message","El usuario no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }

    public ResponseEntity<Object> deleteVendedor(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeVendedor=this.VendedorRepository.existsById(id);
        if(!existeVendedor){
            datos.put("error",true);
            datos.put("message","No existe el tipo de producto con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        VendedorRepository.deleteById(id);
        datos.put("message","Vendedor eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }

}

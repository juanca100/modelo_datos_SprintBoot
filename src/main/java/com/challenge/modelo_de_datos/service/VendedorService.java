package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Vendedor;
import com.challenge.modelo_de_datos.repository.CiudadRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
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
        Integer id=vendedor.getIdVendedor();
        if(id!=0){
            datos.put("error",true);
            datos.put("message", "No mandar ID, este se genera automaticamente");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(vendedor.getClaveVendedor()==null||vendedor.getUsuario()==null||vendedor.getCiudad()==null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla excepto el ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        else{
            if (vendedor.getClaveVendedor().isBlank()) {
                datos.put("error", true);
                datos.put("message", "Los campos de texto no deben estar vacios");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
            else{
                if(UsuarioRepository.existsById(vendedor.getUsuario().getIdUsuario())){
                    if(CiudadRepository.existsById(vendedor.getCiudad().getIdCiudad())){
                        datos.put("message","Se guardo con exito");
                        VendedorRepository.save(vendedor);
                        datos.put("data",vendedor);
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CREATED
                        );
                    }
                    else{
                        datos.put("error",true);
                        datos.put("message","El ID de la ciudad proporcionado es erroneo");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );
                    }
                }
                else{
                    datos.put("error",true);
                    datos.put("message","El ID del usuario proporcionado es erroneo");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
            }
        }
    }

    public ResponseEntity<Object> updateVendedor(Integer id,Vendedor vendedor) {
        HashMap<String,Object> datos= new HashMap<>();
        if(vendedor.getClaveVendedor()==null||vendedor.getUsuario()==null||vendedor.getCiudad()==null) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla excepto el ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        else{
            if(VendedorRepository.existsById(id)){
                vendedor.setIdVendedor(id);
                if (vendedor.getClaveVendedor().isBlank()) {
                    datos.put("error", true);
                    datos.put("message", "Los campos de texto no deben estar vacios");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
                else{
                    if(UsuarioRepository.existsById(vendedor.getUsuario().getIdUsuario())){
                        if(CiudadRepository.existsById(vendedor.getCiudad().getIdCiudad())){
                            datos.put("message","Se actualizo con exito");
                            VendedorRepository.save(vendedor);
                            datos.put("data",vendedor);
                            return new ResponseEntity<>(
                                    datos,
                                    HttpStatus.CREATED
                            );
                        }
                        else{
                            datos.put("error",true);
                            datos.put("message","El ID de la ciudad proporcionado es erroneo");
                            return new ResponseEntity<>(
                                    datos,
                                    HttpStatus.CONFLICT
                            );
                        }
                    }
                    else{
                        datos.put("error",true);
                        datos.put("message","El ID del usuario proporcionado es erroneo");
                        return new ResponseEntity<>(
                                datos,
                                HttpStatus.CONFLICT
                        );
                    }
                }
            }
            else{
                datos.put("error",true);
                datos.put("message","El id proporcionado del Vendedor es erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    public ResponseEntity<Object> deleteVendedor(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!VendedorRepository.existsById(id)){
            datos.put("error",true);
            datos.put("message","No existe vendedor con ese id");
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

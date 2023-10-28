package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TipoProducto;
import com.challenge.modelo_de_datos.repository.CategoriaRepository;
import com.challenge.modelo_de_datos.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TipoProductoService {
    private final TipoProductoRepository TipoProductoRepository;
    private final CategoriaRepository CategoriaRepository;

    @Autowired
    public TipoProductoService(TipoProductoRepository tipoProductoRepository, CategoriaRepository categoriaRepository) {
        TipoProductoRepository = tipoProductoRepository;
        CategoriaRepository = categoriaRepository;
    }

    public List<TipoProducto> getTiposProducto()
    {
        return this.TipoProductoRepository.findAll();
    }

    public ResponseEntity<Object> newTipoProducto(TipoProducto tipoProducto) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existe=this.CategoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria());
        if(existe){
            datos.put("message","Se guardo con exito");
            TipoProductoRepository.save(tipoProducto);
            datos.put("data",tipoProducto);
            return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
                );
            }
        datos.put("message","La categoria no existe");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateTipoProducto(Integer id,TipoProducto tipoProducto) {
        HashMap<String,Object> datos= new HashMap<>();
        boolean existe=this.CategoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria());
        boolean existeTP=this.TipoProductoRepository.existsById(id);
        if(existe){
            if(!existeTP){
                datos.put("error",true);
                datos.put("message","No existe el tipo de producto con ese id");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
            tipoProducto.setIdTipoProducto(id);
            datos.put("message","Se actualizo con exito");
            TipoProductoRepository.save(tipoProducto);
            datos.put("data",tipoProducto);
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CREATED
            );
        }
        datos.put("message","La categoria no existe");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteTipoProducto(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        boolean existeTP=this.TipoProductoRepository.existsById(id);
        if(!existeTP){
            datos.put("error",true);
            datos.put("message","No existe el tipo de producto con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        TipoProductoRepository.deleteById(id);
        datos.put("message","Tipo eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }
}
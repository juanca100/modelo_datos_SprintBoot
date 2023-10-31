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
    private final TipoProductoRepository tipoProductoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public TipoProductoService(TipoProductoRepository tipoProductoRepository, CategoriaRepository categoriaRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<TipoProducto> getTiposProducto() {
        return this.tipoProductoRepository.findAll();
    }

    public ResponseEntity<Object> newTipoProducto(TipoProducto tipoProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existe = this.categoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria());

        if (existe) {
            datos.put("message", "Se guardó con éxito");
            tipoProductoRepository.save(tipoProducto);
            datos.put("data", tipoProducto);
            return new ResponseEntity<>(datos, HttpStatus.CREATED);
        }

        datos.put("message", "La categoría no existe");
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTipoProducto(Integer id, TipoProducto tipoProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existe = this.categoriaRepository.existsById(tipoProducto.getCategoria().getIdCategoria());
        boolean existeTP = this.tipoProductoRepository.existsById(id);

        if (existe) {
            if (!existeTP) {
                datos.put("error", true);
                datos.put("message", "No existe el tipo de producto con ese ID");
                return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
            }

            tipoProducto.setIdTipoProducto(id);
            datos.put("message", "Se actualizó con éxito");
            tipoProductoRepository.save(tipoProducto);
            datos.put("data", tipoProducto);
            return new ResponseEntity<>(datos, HttpStatus.CREATED);
        }

        datos.put("message", "La categoría no existe");
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteTipoProducto(Integer id) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existeTP = this.tipoProductoRepository.existsById(id);

        if (!existeTP) {
            datos.put("error", true);
            datos.put("message", "No existe el tipo de producto con ese ID");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        tipoProductoRepository.deleteById(id);
        datos.put("message", "Tipo eliminado");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
}

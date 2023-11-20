package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TransaccionProducto;
import com.challenge.modelo_de_datos.model.TransaccionProductoId;
import com.challenge.modelo_de_datos.repository.ProductoRepository;
import com.challenge.modelo_de_datos.repository.TransaccionProductoRepository;
import com.challenge.modelo_de_datos.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TransaccionProductoService {
    private final TransaccionProductoRepository transaccionProductoRepository;
    private final TransaccionRepository transaccionRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public TransaccionProductoService(TransaccionProductoRepository transaccionProductoRepository, TransaccionRepository transaccionRepository, ProductoRepository productoRepository) {
        this.transaccionProductoRepository = transaccionProductoRepository;
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
    }

    public List<TransaccionProducto> getTransaccionesProducto() {
        return transaccionProductoRepository.findAll();
    }

    public ResponseEntity<Object> newTransaccionProducto(TransaccionProducto transaccionProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        TransaccionProductoId id=new TransaccionProductoId(transaccionProducto.getTransaccion().getIdTransaccion(),transaccionProducto.getProducto().getIdProducto());
        if (transaccionProductoRepository.existsById(id)) {
            datos.put("error",true);
            datos.put("message", "Esta transaccion ya se guardo en la base de datos");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(transaccionProducto.getProducto()==null||transaccionProducto.getTransaccion()==null||(Float)transaccionProducto.getCantidad()==0) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        else{
            if(transaccionRepository.existsById(transaccionProducto.getTransaccion().getIdTransaccion())){
                if(productoRepository.existsById(transaccionProducto.getProducto().getIdProducto())){
                    transaccionProductoRepository.save(transaccionProducto);
                    datos.put("message", "Se guardó con éxito");
                    datos.put("data", transaccionProducto);
                    return new ResponseEntity<>(datos, HttpStatus.CREATED);
                }
                else{
                    datos.put("error", true);
                    datos.put("message","El producto no existe,ID erroneo");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
            }
            else{
                datos.put("error", true);
                datos.put("message","La transaccion no existe,ID erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    public ResponseEntity<Object> updateTransaccionProducto(Integer idTransaccion,Integer idProducto,TransaccionProducto transaccionProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        TransaccionProductoId id=new TransaccionProductoId(idTransaccion,idProducto);
        if(transaccionProducto.getProducto()==null||transaccionProducto.getTransaccion()==null||(Float)transaccionProducto.getCantidad()==0) {
            datos.put("error", true);
            datos.put("message", "Ingresa todos los campos de la tabla");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        else{
            if(transaccionRepository.existsById(idTransaccion)){
                transaccionProducto.getTransaccion().setIdTransaccion(idTransaccion);
                if(productoRepository.existsById(idProducto)){
                    transaccionProducto.getProducto().setIdProducto(idProducto);
                    if (transaccionProductoRepository.existsById(id)) {
                        transaccionProductoRepository.save(transaccionProducto);
                        datos.put("message", "Se actualizó con éxito");
                        datos.put("data", transaccionProducto);
                        return new ResponseEntity<>(datos, HttpStatus.CREATED);
                    }
                    else{
                        datos.put("error", true);
                        datos.put("message", "No existe la TransaccionProducto con ese ID");
                        return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
                    }
                }
                else{
                    datos.put("error", true);
                    datos.put("message","El producto no existe,ID erroneo");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }
            }
            else{
                datos.put("error", true);
                datos.put("message","La transaccion no existe,ID erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    public ResponseEntity<Object> deleteTransaccionProducto(Integer idTransaccion,Integer idProducto) {
        HashMap<String, Object> datos = new HashMap<>();
        TransaccionProductoId id=new TransaccionProductoId(idTransaccion,idProducto);
        if(transaccionRepository.existsById(idTransaccion)){
            if(productoRepository.existsById(idProducto)){
                if (!transaccionProductoRepository.existsById(id)) {
                    datos.put("error", true);
                    datos.put("message", "No existe la TransaccionProducto con ese ID");
                    return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
                }
                transaccionProductoRepository.deleteById(id);
                datos.put("message", "TransaccionProducto eliminada");
                return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
            }
            else{
                datos.put("error", true);
                datos.put("message","El producto no existe,ID erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }
        else{
            datos.put("error", true);
            datos.put("message","La transaccion no existe,ID erroneo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
    }
}
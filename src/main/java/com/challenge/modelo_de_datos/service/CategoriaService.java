package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Categoria;
import com.challenge.modelo_de_datos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> getCategorias(){
        return this.categoriaRepository.findAll();
    }

    public ResponseEntity<Object> newCategoria(Categoria categoria) {
        HashMap<String,Object> datos= new HashMap<>();
        Integer id= categoria.getIdCategoria();
        //VALIDO EL ID, ES UN CAMPO QUE NO SE CEBE ENVIAR, SI SE ENVÍA ENVIAMOS MENSAJEA DE RROR
        if (id!=0){
            datos.put("error",true);
            datos.put("message","No enviar ID, es un campo auto_increment");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }
        //VALIDAMOS CAMPOS NOT NULL
        if(categoria.getCategoria()==null){
            datos.put("error",true);
            datos.put("message","DEBES INGRESAR EL NOMBRE DE LA CATEGORIA");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );

        }else{
            if (categoria.getCategoria().isBlank()){
                datos.put("error",true);
                datos.put("message","EL CAMPO CATEGORIA NO DEBE ESTAR VACIO");

                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );

            }

            else{
                if (categoria.getCategoria().matches("\\d+")){
                    datos.put("error",true);
                    datos.put("message","LOS CAMPOS VARCHAR NO DEBEN CONTENER NUMEROS");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
                }

                //SE CUMPLIERON TODAS LAS VALIDACIONES

                datos.put("message","Se guardo con exito");
                categoriaRepository.save(categoria);
                datos.put("data",categoria);
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CREATED
                );

            }

        }

    }

    public ResponseEntity<Object> updateCategoria(Integer id,Categoria categoria) {
        HashMap<String,Object> datos= new HashMap<>();

        if(categoria.getCategoria()==null){
            datos.put("error",true);
            datos.put("message","INGRESE EL CAMPO id_categoria");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }else{
            if(categoriaRepository.existsById(id)){
                categoria.setIdCategoria(id);
               if (categoria.getCategoria().isBlank()){
                   datos.put("error",true);
                   datos.put("message","EL CAMPO categoria NO DEBE ESTAR VACIO");
                    return new ResponseEntity<>(
                            datos,
                            HttpStatus.CONFLICT
                    );
               }else{
                   if (categoria.getCategoria().matches("\\d+")){
                       datos.put("error",true);
                       datos.put("message","LOS CAMPOS VARCHAR NO DEBEN CONTENER NUMEROS");
                       return new ResponseEntity<>(
                               datos,
                               HttpStatus.CONFLICT
                       );
                   }

                   datos.put("message","Se actualizo con exito");
                   categoriaRepository.save(categoria);
                   datos.put("data",categoria);
                   return new ResponseEntity<>(
                           datos,
                           HttpStatus.CREATED
                   );
               }

            }else{
                datos.put("error", true);
                datos.put("message","El id de la categoria proporcionado es erroneo");
                return new ResponseEntity<>(
                        datos,
                        HttpStatus.CONFLICT
                );
            }
        }

    }

    public ResponseEntity<Object> deleteCategoria(Integer id){
        HashMap<String,Object> datos= new HashMap<>();
        if(!categoriaRepository.existsById(id)){
            datos.put("error",true);
            datos.put("message","NO EXISTE Categoria CON ESE ID");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        categoriaRepository.deleteById(id);
        datos.put("message","Categoria Eliminada");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }

}


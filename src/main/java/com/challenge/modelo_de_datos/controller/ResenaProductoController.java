package com.challenge.modelo_de_datos.controller;
import com.challenge.modelo_de_datos.model.ResenaProducto;
import com.challenge.modelo_de_datos.service.ResenaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@RestController
@RequestMapping(path = "api/v1/ResenaProducto")
public class ResenaProductoController {

    private final ResenaProductoService resenaProductoService;

    @Autowired
    public ResenaProductoController(ResenaProductoService resenaProductoService) {
        this.resenaProductoService = resenaProductoService;
    }

    @GetMapping
    public List<ResenaProducto> getResenasProducto(){
        return this.resenaProductoService.getResenasProducto();
    }

    @PostMapping(path="/Create")
    @PreAuthorize("hasAnyRole('Admin', 'Usuario','Comprador')")
    public ResponseEntity<Object> addResenaProducto(@RequestBody @Valid ResenaProducto resenaProducto){
        return this.resenaProductoService.newResenaProducto(resenaProducto);
    }

    @PutMapping(path="/Update/{idResenaProducto}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object>updateResena(@PathVariable("idResenaProducto") @NotNull Integer id,@RequestBody @Valid ResenaProducto resenaProducto){
        return this.resenaProductoService.updateResenaProducto(id,resenaProducto);
    }

    @DeleteMapping(path="/Delete/{idResenaProducto}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deleteTransaccion(@PathVariable("idResenaProducto") @NotNull Integer id){
        return this.resenaProductoService.deleteResenaProducto(id);
    }
}

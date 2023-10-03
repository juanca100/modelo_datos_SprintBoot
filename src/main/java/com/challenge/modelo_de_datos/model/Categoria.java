package com.challenge.modelo_de_datos.model;import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="categoria")
public class Categoria {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria")
    int idCategoria;

    String categoria;

    public Categoria(){

    }

    public Categoria(int idCategoria, String categoria) {
        this.idCategoria = idCategoria;
        this.categoria = categoria;
    }
}

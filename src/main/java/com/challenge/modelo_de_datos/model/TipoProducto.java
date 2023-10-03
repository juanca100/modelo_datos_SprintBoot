package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tipo_producto")
public class TipoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_producto")
    int idTipoProducto;
    @Column(name="tipo_producto")
    String tipoProducto;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public TipoProducto(){

    }

    public TipoProducto(int idTipoProducto, String tipoProducto, Categoria categoria) {
        this.idTipoProducto = idTipoProducto;
        this.tipoProducto = tipoProducto;
        this.categoria = categoria;
    }
}

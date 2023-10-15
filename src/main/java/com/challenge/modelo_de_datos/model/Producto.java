package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productos_seq")
    @SequenceGenerator(name = "productos_seq", sequenceName = "productos_seq", allocationSize = 1)
    @Column (name="id_producto")
    int IdProducto;
    @Column(name="nombre")
    String nombre;
    @Column(name="descripcion")
    String descripcion;
    @Column(name="precio")
    float precio;
    @Column(name="detalle")
    String detalle;
    @ManyToOne
    @JoinColumn(name="id_vendedor")
    Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name="id_tipo_producto")
    TipoProducto tipoProducto;



    public Producto() {
    }

}

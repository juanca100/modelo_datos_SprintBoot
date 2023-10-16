package com.challenge.modelo_de_datos.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="resena_producto")
public class ResenaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_resena_producto")
    int idResenaProducto;

    @Column (name="descripcion")
    String descripcion;

    @Column (name="calificacion_producto")
    int calificacionProducto;

    @ManyToOne
    @JoinColumn(name="id_producto")
    Producto producto;

    @ManyToOne
    @JoinColumn(name="id_usuario")
    Usuario usuario;

    public ResenaProducto(){}

    public ResenaProducto(int idResenaProducto, String descripcion, int calificacionProducto, Producto producto, Usuario usuario) {
        this.idResenaProducto = idResenaProducto;
        this.descripcion = descripcion;
        this.calificacionProducto = calificacionProducto;
        this.producto = producto;
        this.usuario = usuario;
    }
}

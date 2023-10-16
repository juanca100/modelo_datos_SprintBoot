package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="resena_tienda")

public class ResenaTienda {

    //atributo pk id_resena_tienda
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena_tienda")
    int idResenaTienda;

    //atributo fk id_usuario
    @ManyToOne
    @JoinColumn(name="id_usuario")
    Usuario usuario;

    //atributo fk id_vendedor
    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    Vendedor vendedor;

    //atributo descripcion
    @Column(name="descripcion")
    String descripcion;

    //atributo calificacion_tienfa
    @Column(name="calificacion_tienda")
    int calificacionTienda;

    public ResenaTienda(int idResenaTienda, Usuario usuario, Vendedor vendedor, String descripcion, int calificacionTienda) {
        this.idResenaTienda = idResenaTienda;
        this.usuario = usuario;
        this.vendedor = vendedor;
        this.descripcion = descripcion;
        this.calificacionTienda = calificacionTienda;
    }

    public ResenaTienda() {

    }
}

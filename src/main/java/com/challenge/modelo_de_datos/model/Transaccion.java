package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name="transaccion")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_transaccion")
    int idTransaccion;
    @Column(name="fecha")
    Date fecha;
    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;
    @ManyToOne
    @JoinColumn(name = "id_comprador")
    private Comprador comprador;

    public Transaccion(){

    }

    public Transaccion(int idTransaccion, Date fecha, Vendedor vendedor, Comprador comprador) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.vendedor = vendedor;
        this.comprador = comprador;
    }
}

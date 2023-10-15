package com.challenge.modelo_de_datos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="transaccion_producto")
public class Transaccion_producto {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_transaccion")
    private Transaccion id_transaccion;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Usuario id_producto;

    @Column(name="cantidad")
    int cantidad;


    public Transaccion_producto(){

    }

    public Transaccion_producto(int cantidad){
        this.cantidad = cantidad;


    }
}

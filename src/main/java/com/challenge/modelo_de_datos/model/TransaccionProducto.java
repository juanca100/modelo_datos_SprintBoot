package com.challenge.modelo_de_datos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(TransaccionProductoId.class)
@Table(name="transaccion_producto")
public class TransaccionProducto {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_transaccion")
    Transaccion transaccion;
    @Id
    @ManyToOne
    @JoinColumn(name = "id_producto")
    Producto producto;

    @Column(name="cantidad")
    float cantidad;

    public TransaccionProducto(){

    }


}

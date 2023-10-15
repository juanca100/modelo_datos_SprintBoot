package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="estado_pago")
public class EstadoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pago")
    int idEstadoPago;

    @Column(name = "estado_pago")
    String estadoPago;


public EstadoPago(){

}

public EstadoPago(int id_estado_pago, String estado_pago){
    this.idEstadoPago = id_estado_pago;
    this.estadoPago = estado_pago;
    }
}
package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="estado_pago")
public class Estado_pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pago")
    int id_tipo_pago;

    @Column(name = "estado_pago")
    String estado_pago;


public Estado_pago(){

}

public Estado_pago(int id_estado_pago, String estado_pago){
    this.id_tipo_pago = id_estado_pago;
    this.estado_pago = estado_pago;
    }
}
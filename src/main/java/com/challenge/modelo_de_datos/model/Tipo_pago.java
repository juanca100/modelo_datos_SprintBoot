package com.challenge.modelo_de_datos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tipo_pago")
public class Tipo_pago {
    @Id
    @Column(name = "id_tipo_pago")
    int idTipoPago;

    @Column(name = "tipo_pago")
    String tipoPago;


    public Tipo_pago(){

    }

    public Tipo_pago(int idTipoPago, String tipoPago){
        this.idTipoPago = idTipoPago;
        this.tipoPago = tipoPago;
    }
}

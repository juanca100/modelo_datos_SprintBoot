package com.challenge.modelo_de_datos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tipo_pago")
public class TipoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_pago")
    int idTipoPago;

    @Column(name = "tipo_pago")
    String tipoPago;


    public TipoPago(){

    }

    public TipoPago(int idTipoPago, String tipoPago){
        this.idTipoPago = idTipoPago;
        this.tipoPago = tipoPago;
    }
}

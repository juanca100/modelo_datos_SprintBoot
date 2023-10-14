package com.challenge.modelo_de_datos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="transaccion_pago")
public class Transaccion_pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    int id_notificacion;

    @Id
    @Column(name = "id_tipo_pago")
    int id_tipo_pago;

    @Id
    @Column(name = "id_estado_pago")
    int id_estado_pago;

    @Column(name = "descripccion")
    String descripccion;

    @Column(name = "monto total")
    boolean monto_total;

    @Id
    @Column(name = "id_transaccion")
    int id_transaccion;


    public Transaccion_pago() {
    }

    public Transaccion_pago(int id_notificacion, int id_tipo_pago, int id_estado_pago, String descripccion, Boolean monto_total, int id_transaccion ) {
        this.id_notificacion = id_notificacion;
        this.id_tipo_pago = id_tipo_pago;
        this.id_estado_pago = id_estado_pago;
        this.descripccion = descripccion;
        this.monto_total = monto_total;
        this.id_transaccion = id_transaccion;
    }
}

package com.challenge.modelo_de_datos.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="transaccion_pago")
public class TransaccionPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion_pago")
    int idTransaccionPago;

    @ManyToOne
    @JoinColumn(name="id_transaccion")
    Transaccion transaccion;

    @Column(name = "monto_total")
    float monto_total;

    @Column(name = "descripccion")
    String descripccion;

    @ManyToOne
    @JoinColumn(name = "id_estado_pago")
    EstadoPago estadoPago;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pago")
    TipoPago tipoPago;

    public TransaccionPago() {
    }

    public TransaccionPago(int idTransaccionPago, Transaccion transaccion, float monto_total, String descripccion, EstadoPago estadoPago, TipoPago tipoPago) {
        this.idTransaccionPago = idTransaccionPago;
        this.transaccion = transaccion;
        this.monto_total = monto_total;
        this.descripccion = descripccion;
        this.estadoPago = estadoPago;
        this.tipoPago = tipoPago;
    }
}

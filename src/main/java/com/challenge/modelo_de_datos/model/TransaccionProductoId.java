package com.challenge.modelo_de_datos.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransaccionProductoId implements Serializable {
    private int transaccion;
    private int producto;

    public TransaccionProductoId(){

    }

    public TransaccionProductoId(int transaccion, int producto) {
        this.transaccion = transaccion;
        this.producto = producto;
    }
}

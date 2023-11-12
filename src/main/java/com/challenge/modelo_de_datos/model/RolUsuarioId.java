package com.challenge.modelo_de_datos.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RolUsuarioId implements Serializable {
    private int rol;
    private int usuario;

    public RolUsuarioId(){

    }

    public RolUsuarioId(int rol, int usuario) {
        this.rol = rol;
        this.usuario = usuario;
    }
}

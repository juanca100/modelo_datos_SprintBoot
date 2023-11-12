package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rol")
    int idRol;
    @Column(name="rol")
    String rol;

    public Rol(){

    }

    public Rol(int idRol, String rol) {
        this.idRol = idRol;
        this.rol = rol;
    }
}

package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    int idUsuario;

    String nombre;

    String correo_electronico;

    String contrasenia;

    public Usuario(){

    }

    public Usuario(int idUsuario, String nombre, String correo_electronico, String contrasenia) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo_electronico = correo_electronico;
        this.contrasenia = contrasenia;
    }
}

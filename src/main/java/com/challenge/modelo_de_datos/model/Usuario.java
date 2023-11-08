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
    @Column(name = "correo_electronico")
    String email;
    @Column(name = "contrasenia")
    String password;

    public Usuario(){

    }

    public Usuario(int idUsuario, String nombre, String email, String password) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
}

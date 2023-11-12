package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(RolUsuarioId.class)
@Table(name="rol_usuario")
public class RolUsuario {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_rol")
    Rol rol;
    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    Usuario usuario;

    public RolUsuario(){

    }
}

package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="notificacion")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_notificacion")
    int idNotificacion;

    @Column(name="descripcion")
    String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_tipo_notificacion")
    private TipoNotificacion tipoNotificacion;

    public Notificacion(){

    }

    public Notificacion(int idNotificacion, String descripcion, Usuario usuario, TipoNotificacion tipoNotificacion) {
        this.idNotificacion = idNotificacion;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.tipoNotificacion = tipoNotificacion;
    }
}

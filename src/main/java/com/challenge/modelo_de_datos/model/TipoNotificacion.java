package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tipo_notificacion")
public class TipoNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_notificacion")
    int idTipoNotificacion;
    @Column(name="tipo_notificacion")
    String tipoNotificacion;

    public TipoNotificacion(){

    }

    public TipoNotificacion(int idTipoNotificacion, String tipoNotificacion) {
        this.idTipoNotificacion = idTipoNotificacion;
        this.tipoNotificacion = tipoNotificacion;
    }
}

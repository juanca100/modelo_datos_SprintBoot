package com.challenge.modelo_de_datos.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.data.web.JsonPath;

import java.sql.Date;
@Getter
@Setter
@Entity
@Table(name="resena_producto")
public class ResenaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_resena_producto")
    int idResenaProducto;

    @Column (name="descripcion")
    String descripcion;

    @Column (name="calificacion_producto")
    int calificacion_producto;

    @ManyToOne
    @JoinColumn(name="id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="id_tipo_producto")
    private TipoProducto tipoProducto;



}

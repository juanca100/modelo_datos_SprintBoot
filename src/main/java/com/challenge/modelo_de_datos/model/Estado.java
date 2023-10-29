package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="estado")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_estado")
    int idEstado;

    @Column(name="estado")
    String estado;

    @ManyToOne
    @JoinColumn(name="id_pais")
    Pais pais;
}

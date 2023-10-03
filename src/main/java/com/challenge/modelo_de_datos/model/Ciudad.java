package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="ciudad")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_ciudad")
    int idCiudad;
    @Column(name="ciudad")
    String ciudad;

    @ManyToOne
    @JoinColumn(name="id_estado")
    Estado estado;
}

package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pais")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pais")
    int idPais;
    @Column(name="pais")
    String pais;
    public Pais(){

    }

    public Pais(int idPais, String pais) {
        this.idPais = idPais;
        this.pais = pais;
    }
}

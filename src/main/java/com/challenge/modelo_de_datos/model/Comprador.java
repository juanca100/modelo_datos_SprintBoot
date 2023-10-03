package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="compradores")
public class Comprador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_comprador")
    int idComprador;
    @Column(name="direccion")
    String direccion;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    public Comprador(){

    }

    public Comprador(int idComprador, String direccion, Usuario usuario, Ciudad ciudad) {
        this.idComprador = idComprador;
        this.direccion = direccion;
        this.usuario = usuario;
        this.ciudad = ciudad;
    }
}

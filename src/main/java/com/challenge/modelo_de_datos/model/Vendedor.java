package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="vendedores")
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_vendedor")
    int idVendedor;
    @Column(name="clave_vendedor")
    String claveVendedor;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    public Vendedor(){

    }

    public Vendedor(int idVendedor, String claveVendedor, Usuario usuario, Ciudad ciudad) {
        this.idVendedor = idVendedor;
        this.claveVendedor = claveVendedor;
        this.usuario = usuario;
        this.ciudad = ciudad;
    }
}


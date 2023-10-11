package com.challenge.modelo_de_datos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_inventario")
    int idInventario;
    @Column(name="stock_inicial")
    int stockInicial;
    @Column(name="stock_minimo")
    int stockMinimo;
    @Column(name="entradas")
    int entrada;
    @Column(name = "salidas")
    int salida;
    @ManyToOne
    @JoinColumn(name="id_producto")
    Producto producto;

}

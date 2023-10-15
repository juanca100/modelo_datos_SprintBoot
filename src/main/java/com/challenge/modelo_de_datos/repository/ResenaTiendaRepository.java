package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.ResenaProducto;
import com.challenge.modelo_de_datos.model.ResenaTienda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResenaTiendaRepository extends JpaRepository<ResenaTienda,Integer> {
}

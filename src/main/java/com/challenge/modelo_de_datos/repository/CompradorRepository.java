package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompradorRepository extends JpaRepository<Comprador,Integer> {
}


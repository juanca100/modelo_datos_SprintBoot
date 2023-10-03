package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository extends JpaRepository<Estado,Integer> {
}
package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
}

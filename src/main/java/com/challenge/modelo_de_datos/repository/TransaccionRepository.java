package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion,Integer> {
}
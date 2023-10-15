package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Estado_pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPagoRepository extends JpaRepository<Estado_pago, Integer> {
}


package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Tipo_pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagoRepository extends JpaRepository<Tipo_pago, Integer> {
}


package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.TransaccionPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionPagoRepository extends JpaRepository<TransaccionPago, Integer> {
}

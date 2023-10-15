package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.TransaccionProducto;
import com.challenge.modelo_de_datos.model.TransaccionProductoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionProductoRepository extends JpaRepository<TransaccionProducto, TransaccionProductoId> {
}


package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor,Integer> {
}

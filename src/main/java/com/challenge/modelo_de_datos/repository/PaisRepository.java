package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais,Integer>
{
}

package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacion,Integer> {
}

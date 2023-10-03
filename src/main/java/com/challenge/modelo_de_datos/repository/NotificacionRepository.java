package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion,Integer>
{
}

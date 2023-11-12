package com.challenge.modelo_de_datos.repository;

import com.challenge.modelo_de_datos.model.RolUsuario;
import com.challenge.modelo_de_datos.model.RolUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolUsuarioRepository extends JpaRepository<RolUsuario, RolUsuarioId> {

}

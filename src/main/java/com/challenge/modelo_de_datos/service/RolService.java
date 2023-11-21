package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.model.Rol;
import com.challenge.modelo_de_datos.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> getRoles(){
        return this.rolRepository.findAll();
    }
}

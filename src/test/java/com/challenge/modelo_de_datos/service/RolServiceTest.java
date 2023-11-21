package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Rol;
import com.challenge.modelo_de_datos.model.TransaccionProducto;
import com.challenge.modelo_de_datos.repository.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RolServiceTest {
    @InjectMocks
    private RolService rolService;

    @Mock
    private RolRepository rolRepository;

    private Rol rol;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        rol=new Rol();
    }

    @Test
    public void getRoles(){
        when(rolRepository.findAll()).thenReturn(Arrays.asList(rol));
        assertNotNull(rolService.getRoles());
    }

}
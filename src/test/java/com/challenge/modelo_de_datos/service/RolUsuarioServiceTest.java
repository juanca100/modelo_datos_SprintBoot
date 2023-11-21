package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.*;
import com.challenge.modelo_de_datos.repository.RolRepository;
import com.challenge.modelo_de_datos.repository.RolUsuarioRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class RolUsuarioServiceTest {
    @InjectMocks
    private RolUsuarioService rolUsuarioService;

    @Mock
    private RolUsuarioRepository rolUsuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private RolUsuario rolUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
       rolUsuario=new RolUsuario();
    }

    @Test
    public void getRolesUsuario(){
        when(rolUsuarioRepository.findAll()).thenReturn(Arrays.asList(rolUsuario));
        assertNotNull(rolUsuarioService.getRolesUsuario());
    }

    @Test
    public void testNewRolUsuario(){
        //Configuraciones Previas
        ResponseEntity<Object> response;

        //Campos Nulos
        response=rolUsuarioService.newRolUsuario(rolUsuario);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));

        //Configuraciones
        rolUsuario.setRol(new Rol());
        rolUsuario.setUsuario(new Usuario());
        rolUsuario.getRol().setIdRol(1);
        rolUsuario.getUsuario().setIdUsuario(1);

        //El id ya existe
        when(rolUsuarioRepository.existsById(any(RolUsuarioId.class))).thenReturn(true);
        response=rolUsuarioService.newRolUsuario(rolUsuario);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Esta asignacion ya se guardo en la base de datos", ((HashMap) response.getBody()).get("message"));

        //El id de Rol existe
        when(rolUsuarioRepository.existsById(any(RolUsuarioId.class))).thenReturn(false);
        when(rolRepository.existsById(anyInt())).thenReturn(false);
        response=rolUsuarioService.newRolUsuario(rolUsuario);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El rol no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        //El id de Usuario existe
        when(rolUsuarioRepository.existsById(any(RolUsuarioId.class))).thenReturn(false);
        when(rolRepository.existsById(anyInt())).thenReturn(true);
        when(usuarioRepository.existsById(anyInt())).thenReturn(false);
        response=rolUsuarioService.newRolUsuario(rolUsuario);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El usuario no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        //Exito
        when(rolUsuarioRepository.existsById(any(RolUsuarioId.class))).thenReturn(false);
        when(rolRepository.existsById(anyInt())).thenReturn(true);
        when(usuarioRepository.existsById(anyInt())).thenReturn(true);
        response=rolUsuarioService.newRolUsuario(rolUsuario);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se asigno con éxito", ((HashMap) response.getBody()).get("message"));
    }

    @Test
    public void testDeleteTransaccionProducto(){
        //Configuraciones Previas
        ResponseEntity<Object> response;

        //Fracaso
        when(rolUsuarioRepository.existsById(any(RolUsuarioId.class))).thenReturn(false);
        response=rolUsuarioService.deleteRolUsuario(1,1);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("No existe la relacion con ese ID", ((HashMap) response.getBody()).get("message"));

        //Exito
        when(rolUsuarioRepository.existsById(any(RolUsuarioId.class))).thenReturn(true);
        response=rolUsuarioService.deleteRolUsuario(1,1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Asignacion eliminada", ((HashMap) response.getBody()).get("message"));
    }
}
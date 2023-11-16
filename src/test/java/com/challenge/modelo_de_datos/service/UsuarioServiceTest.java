package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.TipoPago;
import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.repository.TipoPagoRepository;
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
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuario = new Usuario();
        usuario.setEmail("prueba");
        usuario.setNombre("prueba");
        usuario.setPassword("prueba");
    }

    @Test
    void getUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));
        assertNotNull(usuarioService.getUsuarios());
    }


    @Test
    void newUsuario() {
        //TEST CUANDO EL ID NO ES 0
        usuario.setIdUsuario(1);
        ResponseEntity<Object> responseWithNonZeroId = usuarioService.newUsuario(usuario);
        assertEquals(HttpStatus.BAD_REQUEST, responseWithNonZeroId.getStatusCode());
        assertTrue(responseWithNonZeroId.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS SON NULOS
        usuario.setIdUsuario(0);
        usuario.setNombre(null);
        usuario.setEmail(null);
        usuario.setPassword(null);
        ResponseEntity<Object> responseWithNullUsuario = usuarioService.newUsuario(usuario);
        assertEquals(HttpStatus.CONFLICT, responseWithNullUsuario.getStatusCode());
        assertTrue(responseWithNullUsuario.getBody() instanceof HashMap);

        // TEST CUANDO LOS CAMPOS ESTAN EN BLANCOS
        usuario.setNombre("");
        usuario.setEmail("");
        usuario.setPassword("");
        ResponseEntity<Object> responseWithBlankUsuario = usuarioService.newUsuario(usuario);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankUsuario.getStatusCode());
        assertTrue(responseWithBlankUsuario.getBody() instanceof HashMap);

        // TEST DE COMPROBACION DE NUMEROS
        usuario.setNombre("123");
        usuario.setEmail("123");
        usuario.setPassword("123");

        ResponseEntity<Object> responseWithNumericUsuario = usuarioService.newUsuario(usuario);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericUsuario.getStatusCode());
        assertTrue(responseWithNumericUsuario.getBody() instanceof HashMap);

        // TEST DE VALIDACION
        usuario.setNombre("valid");
        usuario.setEmail("valid");
        usuario.setPassword("valid");

        ResponseEntity<Object> responseWithUsuario = usuarioService.newUsuario(usuario);
        assertEquals(HttpStatus.CREATED, responseWithUsuario.getStatusCode());
        assertTrue(responseWithUsuario.getBody() instanceof HashMap);

    }

    @Test
    void updateUsuario() {
        // Configuración del escenario
        int idUsuarioExistente= 1;
        int idUsuarioNoExistente = 50; // ID que no existe

        when(usuarioRepository.existsById(idUsuarioExistente)).thenReturn(true);

        // Caso cuando el campo 'pais' es nulo
        usuario.setIdUsuario(idUsuarioExistente);
        usuario.setNombre(null);
        usuario.setEmail(null);
        usuario.setPassword(null);

        ResponseEntity<Object> responseWithNullUsuario = usuarioService.updateUsuario(idUsuarioExistente, usuario);
        assertEquals(HttpStatus.CONFLICT, responseWithNullUsuario.getStatusCode());
        assertTrue(responseWithNullUsuario.getBody() instanceof HashMap);

        // Caso cuando el campo 'pais' está en blanco
        usuario.setNombre("");
        usuario.setEmail("");
        usuario.setPassword("");
        ResponseEntity<Object> responseWithBlankUsuario = usuarioService.updateUsuario(idUsuarioExistente, usuario);
        assertEquals(HttpStatus.CONFLICT, responseWithBlankUsuario.getStatusCode());
        assertTrue(responseWithBlankUsuario.getBody() instanceof HashMap);

        // Caso cuando el campo 'pais' contiene números
        usuario.setNombre("123");
        usuario.setEmail("123");
        usuario.setPassword("123");
        ResponseEntity<Object> responseWithNumericUsuario = usuarioService.updateUsuario(idUsuarioExistente, usuario);
        assertEquals(HttpStatus.CONFLICT, responseWithNumericUsuario.getStatusCode());
        assertTrue(responseWithNumericUsuario.getBody() instanceof HashMap);

        // Caso cuando la actualización es exitosa
        usuario.setNombre("valid");
        usuario.setEmail("valid");
        usuario.setPassword("valid");
        ResponseEntity<Object> responseWithValidUsuario = usuarioService.updateUsuario(idUsuarioExistente, usuario);
        assertEquals(HttpStatus.CREATED, responseWithValidUsuario.getStatusCode());
        assertTrue(responseWithValidUsuario.getBody() instanceof HashMap);

        // Caso cuando el ID no existe
        ResponseEntity<Object> responseWithNonExistingId = usuarioService.updateUsuario(idUsuarioNoExistente, usuario);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);

        // Verificar que el método save se haya llamado una vez
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void deleteUsuario() {
        // Configuración del repositorio mock
        int idToDelete = 1;
        when(usuarioRepository.existsById(idToDelete)).thenReturn(true);

        // Prueba cuando el ID existe
        ResponseEntity<Object> responseWithExistingId = usuarioService.deleteUsuario(idToDelete);
        assertEquals(HttpStatus.ACCEPTED, responseWithExistingId.getStatusCode());
        assertTrue(responseWithExistingId.getBody() instanceof HashMap);

        // Verificar que se llamó a deleteById con el ID proporcionado
        verify(usuarioRepository, times(1)).deleteById(idToDelete);

        // Prueba cuando el ID no existe
        int nonExistingId = 2;
        when(usuarioRepository.existsById(nonExistingId)).thenReturn(false);

        ResponseEntity<Object> responseWithNonExistingId = usuarioService.deleteUsuario(nonExistingId);
        assertEquals(HttpStatus.CONFLICT, responseWithNonExistingId.getStatusCode());
        assertTrue(responseWithNonExistingId.getBody() instanceof HashMap);
    }
}
package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Ciudad;
import com.challenge.modelo_de_datos.model.Comprador;
import com.challenge.modelo_de_datos.model.Estado;
import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.repository.CiudadRepository;
import com.challenge.modelo_de_datos.repository.CompradorRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import org.checkerframework.checker.units.qual.C;
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

class CompradorServiceTest {
    @Mock
    private CompradorRepository compradorRepository;

    @Mock
    private CiudadRepository ciudadRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CompradorService compradorService;
    private Ciudad ciudad;
    private Usuario usuario;
    private Comprador comprador;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        comprador = new Comprador();
        comprador.setIdComprador(1);
        comprador.setCiudad(new Ciudad());
        comprador.setDireccion("prueba");
        comprador.setUsuario(new Usuario());
    }

    @Test
    void getCompradores() {
        when(compradorRepository.findAll()).thenReturn(Arrays.asList(comprador));
        assertNotNull(compradorService.getCompradores());
    }

    /*newComprador*/
    @Test
    public void testNewComprador_Success() {
        // Crea una instancia de Ciudad para la prueba
        Comprador comprador = new Comprador();
        comprador.setCiudad(new Ciudad());
        comprador.setDireccion("prueba");
        comprador.setUsuario(new Usuario());

        // Configura el comportamiento del repositorio de estado
        when(usuarioRepository.existsById(comprador.getUsuario().getIdUsuario())).thenReturn(true);
        when(ciudadRepository.existsById(comprador.getCiudad().getIdCiudad())).thenReturn(true);
        // Configura el comportamiento del repositorio de ciudad
        when(compradorRepository.save(comprador)).thenReturn(comprador);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.newComprador(comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio se haya llamado
        verify(compradorRepository, times(1)).save(comprador);
    }

    @Test
    public void testNewComprador_BadRequest_InvalidId() {
        // Crea una instancia de Comprador para la prueba con ID no permitido
        Comprador comprador = new Comprador();
        comprador.setIdComprador(1);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.newComprador(comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }

    @Test
    public void testNewComprador_BadRequest_NullFields() {
        // Crea una instancia de Comprador para la prueba con campos nulos
        Comprador comprador = new Comprador();

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.newComprador(comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }

    @Test
    public void testNewComprador_Conflict_BlankDireccion() {
        // Crea una instancia de Comprador para la prueba con dirección en blanco
        Comprador comprador = new Comprador();
        comprador.setCiudad(new Ciudad());
        comprador.setUsuario(new Usuario());
        comprador.setDireccion("");

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.newComprador(comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }

    @Test
    public void testNewComprador_Conflict_NumericDireccion() {
        // Crea una instancia de Comprador para la prueba con dirección numérica
        Comprador comprador = new Comprador();
        comprador.setCiudad(new Ciudad());
        comprador.setUsuario(new Usuario());
        comprador.setDireccion("123");

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.newComprador(comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }

    @Test
    public void testNewComprador_Created_InvalidUsuarioId() {
        // Crea una instancia de Comprador para la prueba con ID de usuario no existente
        Comprador comprador = new Comprador();
        comprador.setCiudad(new Ciudad());
        comprador.setDireccion("prueba");
        comprador.setUsuario(new Usuario());

        // Configura el comportamiento del repositorio de estado
        when(usuarioRepository.existsById(comprador.getUsuario().getIdUsuario())).thenReturn(false);
        // Configura el comportamiento del repositorio de ciudad
        when(compradorRepository.save(comprador)).thenReturn(comprador);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.newComprador(comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("El usuario no existe, ID erroneo", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }

    @Test
    public void testNewComprador_Created_InvalidCiudadId() {
        // Crea una instancia de Comprador para la prueba con ID de ciudad no existente
        Comprador comprador = new Comprador();
        comprador.setCiudad(new Ciudad());
        comprador.setUsuario(new Usuario());
        comprador.setDireccion("Direccion Ejemplo");

        // Configura el comportamiento del repositorio de ciudad
        when(ciudadRepository.existsById(comprador.getCiudad().getIdCiudad())).thenReturn(false);

        // Configura el comportamiento del repositorio de usuario
        when(usuarioRepository.existsById(comprador.getUsuario().getIdUsuario())).thenReturn(true);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.newComprador(comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("La Ciudad no existe, ID erroneo", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }

    /*fin comprador*/

    /*update comprador*/
    @Test
    public void testUpdateComprador_Success() {
        // Crea una instancia de Comprador para la prueba
        Comprador comprador = new Comprador();
        comprador.setIdComprador(1); // Establece un ID existente
        comprador.setCiudad(new Ciudad());
        comprador.setDireccion("nuevaDireccion");
        comprador.setUsuario(new Usuario());

        // Configura el comportamiento del repositorio de estado
        when(compradorRepository.existsById(comprador.getIdComprador())).thenReturn(true);
        when(usuarioRepository.existsById(comprador.getUsuario().getIdUsuario())).thenReturn(true);
        when(ciudadRepository.existsById(comprador.getCiudad().getIdCiudad())).thenReturn(true);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.updateComprador(1,comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardó con éxito", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio se haya llamado
        verify(compradorRepository, times(1)).save(comprador);
    }

    @Test
    public void testUpdateComprador_NotFound() {
        // Crea una instancia de Comprador para la prueba con ID no existente
        Comprador comprador = new Comprador();
        comprador.setIdComprador(1); // Establece un ID no existente
        comprador.setCiudad(new Ciudad());
        comprador.setDireccion("prueba");
        comprador.setUsuario(new Usuario());

        // Configura el comportamiento del repositorio de estado
        when(compradorRepository.existsById(comprador.getIdComprador())).thenReturn(false);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.updateComprador(1,comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id del comprador proporcionado es erroneo", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio NO se haya llamado
        verify(compradorRepository, never()).save(any(Comprador.class));
    }
    @Test
    public void testUpdateComprador_Created_InvalidCiudadId() {
        // Crea una instancia de Comprador para la prueba con ID de ciudad no existente
        Comprador comprador = new Comprador();
        comprador.setIdComprador(1);
        comprador.setCiudad(new Ciudad());
        comprador.setUsuario(new Usuario());
        comprador.setDireccion("Direccion Ejemplo");
        when(compradorRepository.existsById(comprador.getIdComprador())).thenReturn(true);

        // Configura el comportamiento del repositorio de usuario
        when(usuarioRepository.existsById(comprador.getUsuario().getIdUsuario())).thenReturn(true);
        // Configura el comportamiento del repositorio de ciudad
        when(ciudadRepository.existsById(comprador.getCiudad().getIdCiudad())).thenReturn(false);



        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.updateComprador(1,comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("La Ciudad no existe, ID erroneo", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }

    @Test
    public void testUpdateComprador_Created_InvalidUsuarioId() {
        // Crea una instancia de Comprador para la prueba con ID de usuario no existente
        Comprador comprador = new Comprador();
        comprador.setIdComprador(1);
        comprador.setCiudad(new Ciudad());
        comprador.setDireccion("prueba");
        comprador.setUsuario(new Usuario());
        when(compradorRepository.existsById(comprador.getIdComprador())).thenReturn(true);
        // Configura el comportamiento del repositorio de estado
        when(usuarioRepository.existsById(comprador.getUsuario().getIdUsuario())).thenReturn(false);
        // Configura el comportamiento del repositorio de ciudad
        when(compradorRepository.save(comprador)).thenReturn(comprador);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.updateComprador(1,comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("El usuario no existe, ID erroneo", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }

    @Test
    public void testUpdateComprador_Conflict_BlankDireccion() {
        // Crea una instancia de Comprador para la prueba con dirección en blanco
        Comprador comprador = new Comprador();
        comprador.setIdComprador(1);
        comprador.setCiudad(new Ciudad());
        comprador.setUsuario(new Usuario());
        comprador.setDireccion("");
        when(compradorRepository.existsById(comprador.getIdComprador())).thenReturn(true);
        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.updateComprador(1,comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }
    @Test
    public void testUpdateComprador_Conflict_NumericDireccion() {
        // Crea una instancia de Comprador para la prueba con dirección numérica
        Comprador comprador = new Comprador();
        comprador.setIdComprador(1);
        comprador.setCiudad(new Ciudad());
        comprador.setUsuario(new Usuario());
        comprador.setDireccion("123");
        when(compradorRepository.existsById(comprador.getIdComprador())).thenReturn(true);
        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.updateComprador(1,comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }
    @Test
    public void testUpdateComprador_BadRequest_NullFields() {
        // Crea una instancia de Comprador para la prueba con campos nulos
        Comprador comprador = new Comprador();

        // Llama al método que quieres probar
        ResponseEntity<Object> response = compradorService.updateComprador(1,comprador);

        // Verifica el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(compradorRepository, never()).save(comprador);
    }


    /*fin update */

    /*delete inicio*/
    @Test
    void deleteComprador() {
        when(compradorRepository.existsById(comprador.getIdComprador())).thenReturn(true);
        ResponseEntity<Object> response =compradorService.deleteComprador(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Comprador eliminado",((HashMap) response.getBody()).get("message"));
    }
    @Test
    void deleteComprador_error(){
        when(compradorRepository.existsById(comprador.getIdComprador())).thenReturn(false);
        ResponseEntity<Object> response=compradorService.deleteComprador(1);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("No existe el comprador con ese ID",((HashMap) response.getBody()).get("message"));
    }


    /*fin*/
}
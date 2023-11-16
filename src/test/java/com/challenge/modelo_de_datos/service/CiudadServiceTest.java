package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Ciudad;
import com.challenge.modelo_de_datos.model.Estado;
import com.challenge.modelo_de_datos.repository.CiudadRepository;
import com.challenge.modelo_de_datos.repository.EstadoRepository;
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

class CiudadServiceTest {
    @Mock
    private CiudadRepository ciudadRepository;
    @InjectMocks
    private CiudadService ciudadService;

    @Mock
    private EstadoRepository estadoRepository;
    @InjectMocks
    private  EstadoService estadoService;
    private Ciudad ciudad;
    private Estado estado;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ciudad = new Ciudad();
        ciudad.setCiudad("prueba");
        ciudad.setIdCiudad(1);
    }

    @Test
    void getCiudades() {
        when(ciudadRepository.findAll()).thenReturn(Arrays.asList(ciudad));
        assertNotNull(ciudadService.getCiudades());
    }

    /*newCiudad */
    @Test
    public void testNewCiudad_Success() {
        // Crea una instancia de Ciudad para la prueba
        Ciudad ciudad = new Ciudad();
        ciudad.setCiudad("Ejemplo Ciudad");
        ciudad.setEstado(new Estado());

        // Configura el comportamiento del repositorio de estado
        when(estadoRepository.existsById(ciudad.getEstado().getIdEstado())).thenReturn(true);

        // Configura el comportamiento del repositorio de ciudad
        when(ciudadRepository.save(ciudad)).thenReturn(ciudad);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.newCiudad(ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("SE GUARDO LA CIUDAD CON EXITO", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio se haya llamado
        verify(ciudadRepository, times(1)).save(ciudad);
    }

    @Test
    public void testNewCiudad_BadRequest_NoId() {
        // Crea una instancia de Ciudad para la prueba con ID no permitido
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.newCiudad(ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }

    @Test
    public void testNewCiudad_BadRequest_NullCiudadAndEstado() {
        // Crea una instancia de Ciudad para la prueba con campos nulos
        Ciudad ciudad = new Ciudad();

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.newCiudad(ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }

    @Test
    public void testNewCiudad_Conflict_BlankCiudad() {
        // Crea una instancia de Ciudad para la prueba con campo Ciudad en blanco
        Ciudad ciudad = new Ciudad();
        ciudad.setCiudad("");
        ciudad.setEstado(new Estado());

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.newCiudad(ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben estar vacios", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }

    @Test
    public void testNewCiudad_Conflict_NumericCiudad() {
        // Crea una instancia de Ciudad para la prueba con campo Ciudad numérico
        Ciudad ciudad = new Ciudad();
        ciudad.setCiudad("123");
        ciudad.setEstado(new Estado());

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.newCiudad(ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }


    @Test
    public void testNewCiudad_Conflict_InvalidEstadoId() {
        // Crea una instancia de Ciudad para la prueba con ID de estado no existente
        Ciudad ciudad = new Ciudad();
        ciudad.setCiudad("Ejemplo Ciudad");
        ciudad.setEstado(new Estado());

        // Configura el comportamiento del repositorio de estado
        when(estadoRepository.existsById(ciudad.getEstado().getIdEstado())).thenReturn(false);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.newCiudad(ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("El pais no existe, ID erroneo", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }


    /* fin de newCiudad*/
    @Test
    void deleteCiudad(){
       // Configura el comportamiento del repositorio de ciudad
       when(ciudadRepository.existsById(anyInt())).thenReturn(true);

       // Llama al método que quieres probar
       ResponseEntity<Object> response = ciudadService.deleteCiudad(1);

       // Verifica el resultado
       assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
       assertEquals("CIUDAD ELIMINADA", ((HashMap) response.getBody()).get("message"));

       // Verifica que el método deleteById del repositorio se haya llamado
       verify(ciudadRepository, times(1)).deleteById(1);
   }

   /*update ciudad*/
   @Test
   public void testUpdateCiudad_Success() {
       // Crea una instancia de Ciudad para la prueba
       Ciudad ciudad = new Ciudad();
       ciudad.setIdCiudad(1);
       ciudad.setCiudad("Ejemplo Ciudad");
       ciudad.setEstado(new Estado());

       // Configura el comportamiento del repositorio de ciudad
       when(ciudadRepository.existsById(ciudad.getIdCiudad())).thenReturn(true);

       // Configura el comportamiento del repositorio de estado
       when(estadoRepository.existsById(ciudad.getEstado().getIdEstado())).thenReturn(true);

       // Llama al método que quieres probar
       ResponseEntity<Object> response = ciudadService.updateCiudad(1, ciudad);

       // Verifica el resultado
       assertEquals(HttpStatus.CREATED, response.getStatusCode());
       assertEquals("SE ACTUALIZO CON EXITO", ((HashMap) response.getBody()).get("message"));

       // Verifica que el método save del repositorio se haya llamado
       verify(ciudadRepository, times(1)).save(ciudad);
   }

    @Test
    public void testUpdateCiudad_Conflict_InvalidId() {
        // Crea una instancia de Ciudad para la prueba
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1);
        ciudad.setEstado(new Estado());

        // Configura el comportamiento del repositorio de ciudad
        when(ciudadRepository.existsById(ciudad.getIdCiudad())).thenReturn(false);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.updateCiudad(1, ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id de la ciudad proporcionado es erroneo", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }

    @Test
    public void testUpdateCiudad_Conflict_EmptyCiudad() {
        // Crea una instancia de Ciudad para la prueba
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1);
        ciudad.setCiudad("");

        // Configura el comportamiento del repositorio de ciudad
        when(ciudadRepository.existsById(ciudad.getIdCiudad())).thenReturn(true);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.updateCiudad(1, ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("LOS CAMPOS DE CARACTERES NO DEBEN ESTAR VACIOS", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }

    @Test
    public void testUpdateCiudad_Conflict_NumericCiudad() {
        // Crea una instancia de Ciudad para la prueba
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1);
        ciudad.setCiudad("123");

        // Configura el comportamiento del repositorio de ciudad
        when(ciudadRepository.existsById(ciudad.getIdCiudad())).thenReturn(true);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.updateCiudad(1, ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Las campos de caracteres no deben ser numeros", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }

    @Test
    public void testUpdateCiudad_Conflict_InvalidEstadoId() {
        // Crea una instancia de Ciudad para la prueba
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1);
        ciudad.setCiudad("Ejemplo Ciudad");
        ciudad.setEstado(new Estado());

        // Configura el comportamiento del repositorio de ciudad
        when(ciudadRepository.existsById(ciudad.getIdCiudad())).thenReturn(true);

        // Configura el comportamiento del repositorio de estado
        when(estadoRepository.existsById(ciudad.getEstado().getIdEstado())).thenReturn(false);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.updateCiudad(1, ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El pais no existe,ID erroneo", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }

    @Test
    public void testUpdateCiudad_BadRequest_NullCiudadAndEstado() {
        // Crea una instancia de Ciudad para la prueba
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1);

        // Llama al método que quieres probar
        ResponseEntity<Object> response = ciudadService.updateCiudad(1, ciudad);

        // Verifica el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("INGRESA TODOS LOS CAMPOS DE LA TABLA", ((HashMap) response.getBody()).get("message"));

        // Verifica que el método save del repositorio no se haya llamado
        verify(ciudadRepository, never()).save(ciudad);
    }
    /*fin update ciudad*/
   @Test
    void deleteCiudadError(){
       when(ciudadRepository.existsById(anyInt())).thenReturn(false);

       // Llama al método que quieres probar
       ResponseEntity<Object> response = ciudadService.deleteCiudad(1);

       // Verifica el resultado
       assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
       assertEquals("No existe ciudad con ese id", ((HashMap) response.getBody()).get("message"));

       // Verifica que el método deleteById del repositorio no se haya llamado
       verify(ciudadRepository, never()).deleteById(anyInt());
   }

   }


package com.challenge.modelo_de_datos.service;

import com.challenge.modelo_de_datos.model.Ciudad;
import com.challenge.modelo_de_datos.model.Pais;
import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.model.Vendedor;
import com.challenge.modelo_de_datos.repository.CiudadRepository;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import com.challenge.modelo_de_datos.repository.VendedorRepository;
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

class VendedorServiceTest {
    @Mock
    private VendedorRepository vendedorRepository;
    @Mock
    private CiudadRepository ciudadRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private VendedorService vendedorService;
    private Ciudad ciudad;
    private Usuario usuario;
    private Vendedor vendedor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendedor=new Vendedor();
    }

    @Test
    void getVendedores() {
        when(vendedorRepository.findAll()).thenReturn(Arrays.asList(vendedor));
        assertNotNull(vendedorService.getVendedores());
    }
    /*new vendedor*/
    @Test
    public void testNewVendedor_Success() {
        vendedor.setClaveVendedor("prueba");
        vendedor.setCiudad(new Ciudad());
        vendedor.setUsuario(new Usuario());
        when(ciudadRepository.existsById(vendedor.getCiudad().getIdCiudad())).thenReturn(true);
        when(usuarioRepository.existsById(vendedor.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.save(vendedor)).thenReturn(vendedor);
        ResponseEntity<Object> response = vendedorService.newVendedor(vendedor);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se guardo con exito", ((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, times(1)).save(vendedor);
    }
    @Test
    public void testNewVendedor_BadRequest_InvalidId() {
        vendedor.setIdVendedor(1);
        ResponseEntity<Object> response = vendedorService.newVendedor(vendedor);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No mandar ID, este se genera automaticamente", ((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }
    @Test
    public void testNewVendedor_BadRequest_NullFields() {
        vendedor.setClaveVendedor(null);
        vendedor.setCiudad(null);
        vendedor.setUsuario(null);
        ResponseEntity<Object> response = vendedorService.newVendedor(vendedor);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla excepto el ID", ((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }
    @Test
    public void testNewVendedor_Conflict_Blank() {
        vendedor.setClaveVendedor("");
        vendedor.setCiudad(new Ciudad());
        vendedor.setUsuario(new Usuario());
        ResponseEntity<Object> response = vendedorService.newVendedor(vendedor);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de texto no deben estar vacios", ((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }

    @Test
    public void testNewVendedor_IdUsuario_Invalid(){
        vendedor.setUsuario(new Usuario());
        vendedor.setClaveVendedor("prueba");
        vendedor.setCiudad(new Ciudad());
        when(vendedorRepository.existsById(vendedor.getUsuario().getIdUsuario())).thenReturn(false);
        ResponseEntity<Object> response=vendedorService.newVendedor(vendedor);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El ID del usuario proporcionado es erroneo",((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }
    @Test
    public void testNewVendedor_IdCiudad_Invalid(){
        vendedor.setUsuario(new Usuario());
        vendedor.setClaveVendedor("prueba");
        vendedor.setCiudad(new Ciudad());
        when(usuarioRepository.existsById(vendedor.getUsuario().getIdUsuario())).thenReturn(true);
        when(ciudadRepository.existsById(vendedor.getCiudad().getIdCiudad())).thenReturn(false);
        ResponseEntity<Object> response=vendedorService.newVendedor(vendedor);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El ID de la ciudad proporcionado es erroneo",((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }
    /*fin new vendedor*/
    /*Update Vendedor*/
    @Test
    public void testUpdateVendedor_Success() {
        vendedor.setIdVendedor(1);
        vendedor.setClaveVendedor("prueba");
        vendedor.setCiudad(new Ciudad());
        vendedor.setUsuario(new Usuario());
        when(vendedorRepository.existsById(vendedor.getIdVendedor())).thenReturn(true);
        when(ciudadRepository.existsById(vendedor.getCiudad().getIdCiudad())).thenReturn(true);
        when(usuarioRepository.existsById(vendedor.getUsuario().getIdUsuario())).thenReturn(true);
        when(vendedorRepository.save(vendedor)).thenReturn(vendedor);
        ResponseEntity<Object> response = vendedorService.updateVendedor(1,vendedor);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Se actualizo con exito", ((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, times(1)).save(vendedor);
    }
    @Test
    public void testUpdateVendedor_BadRequest_InvalidId() {
        vendedor.setIdVendedor(1);
        vendedor.setClaveVendedor("prueba");
        vendedor.setCiudad(new Ciudad());
        vendedor.setUsuario(new Usuario());
        when(vendedorRepository.existsById(vendedor.getIdVendedor())).thenReturn(false);
        ResponseEntity<Object> response = vendedorService.updateVendedor(1,vendedor);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El id proporcionado del Vendedor es erroneo", ((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }
    @Test
    public void testUpdateVendedor_BadRequest_NullFields() {
        vendedor.setClaveVendedor(null);
        vendedor.setCiudad(null);
        vendedor.setUsuario(null);
        ResponseEntity<Object> response = vendedorService.updateVendedor(1,vendedor);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ingresa todos los campos de la tabla excepto el ID", ((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }
    @Test
    public void testUpdateVendedor_Conflict_Blank() {
        vendedor.setClaveVendedor("");
        vendedor.setIdVendedor(1);
        vendedor.setCiudad(new Ciudad());
        vendedor.setUsuario(new Usuario());
        when(vendedorRepository.existsById(vendedor.getIdVendedor())).thenReturn(true);
        ResponseEntity<Object> response = vendedorService.updateVendedor(1,vendedor);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Los campos de texto no deben estar vacios", ((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }

    @Test
    public void testUpdateVendedor_IdUsuario_Invalid(){
        vendedor.setIdVendedor(1);
        vendedor.setUsuario(new Usuario());
        vendedor.setClaveVendedor("prueba");
        vendedor.setCiudad(new Ciudad());
        when(vendedorRepository.existsById(vendedor.getIdVendedor())).thenReturn(true);
        when(usuarioRepository.existsById(vendedor.getUsuario().getIdUsuario())).thenReturn(false);
        ResponseEntity<Object> response=vendedorService.updateVendedor(1,vendedor);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El ID del usuario proporcionado es erroneo",((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }
    @Test
    public void testUpdateVendedor_IdCiudad_Invalid(){
        vendedor.setIdVendedor(1);
        vendedor.setUsuario(new Usuario());
        vendedor.setClaveVendedor("prueba");
        vendedor.setCiudad(new Ciudad());
        when(vendedorRepository.existsById(vendedor.getIdVendedor())).thenReturn(true);
        when(ciudadRepository.existsById(vendedor.getCiudad().getIdCiudad())).thenReturn(false);
        when(usuarioRepository.existsById(vendedor.getUsuario().getIdUsuario())).thenReturn(true);
        ResponseEntity<Object> response=vendedorService.updateVendedor(1,vendedor);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("El ID de la ciudad proporcionado es erroneo",((HashMap) response.getBody()).get("message"));
        verify(vendedorRepository, never()).save(vendedor);
    }
    /*fin update vendedor*/
    /*delete vendedor*/
    @Test
    void deleteEstado() {
        vendedor.setIdVendedor(1);
        when(vendedorRepository.existsById(vendedor.getIdVendedor())).thenReturn(true);
        ResponseEntity<Object> response =vendedorService.deleteVendedor(1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Vendedor eliminado",((HashMap) response.getBody()).get("message"));
    }
    @Test
    void deleteEstado_error(){
        vendedor.setIdVendedor(1);
        when(vendedorRepository.existsById(vendedor.getIdVendedor())).thenReturn(false);
        ResponseEntity<Object> response=vendedorService.deleteVendedor(1);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("No existe vendedor con ese id",((HashMap) response.getBody()).get("message"));
    }
    /* fin delete vendedor*/
}
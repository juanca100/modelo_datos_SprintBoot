package com.challenge.modelo_de_datos.security;
import lombok.Data;
//Clase para manejar las credenciales del login correo y contraseña
@Data
public class AuthCredentials {
    private String email;
    private String password;
}

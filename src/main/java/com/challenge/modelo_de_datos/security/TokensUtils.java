package com.challenge.modelo_de_datos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Key;
import java.time.LocalDate;
import java.util.*;

//Nota el manejo de roles en token se hace aqui
//Clase para generar tokens
public class TokensUtils {

    private static final Logger LOG = LoggerFactory.getLogger(TokensUtils.class);

    private final static String ACCESS_TOKEN_SECRET = "yu6bfbiEK3khm4NbITvqShoQ1pP4wKAO8H9EGMOukjqkr66vF/3Ah4JsRanbD/Fe\n";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_00L;

    //Generar el token con el nombre e email encriptado
    public static String createToken(String nombre, String email) {
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);
        LOG.info("Encriptar usuario: "+nombre+" y "+email);
        //extra.put("roles",roles) se deben recuperar los roles con el correo electronico si se mandan al token
        return Jwts.builder()
                .setSubject(email)//añadir usuario
                .setExpiration(expirationDate)//fecha de expiracion de token
                .addClaims(extra)//añadir nombre
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))//firma del token
                .compact();

    }
    //Desencriptar el token empleando la firma
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
      try {
          Claims claims = Jwts.parserBuilder()//desencriptartoken
                  .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())//firma
                  .build()
                  .parseClaimsJws(token)//mandar a claims los campos del token desencriptado
                  .getBody();
          String email = claims.getSubject();//obtener correo electronico desencriptado del token
          LOG.debug("Desencriptando "+claims.getSubject());
          return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());//devolver usuario
                                                        //el primero es correo, el segundo campo es contraseña y tercero los privilegios
      }catch (JwtException e){
          LOG.error(e.getMessage());
          return null;
      }
    }
}

package com.challenge.modelo_de_datos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials=new AuthCredentials();
        try{
            authCredentials=new  ObjectMapper().readValue(request.getReader(),AuthCredentials.class);
        }catch (IOException e){

        }
        UsernamePasswordAuthenticationToken usernamePAT=new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()//roles
        );
        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails=(UserDetailsImpl) authResult.getPrincipal();
        String token = TokensUtils.createToken(userDetails.getNombre(),userDetails.getUsername());
        response.addHeader("Authorization","Bearer "+ token);
        response.getWriter().flush();//escribir toda la respuesta
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
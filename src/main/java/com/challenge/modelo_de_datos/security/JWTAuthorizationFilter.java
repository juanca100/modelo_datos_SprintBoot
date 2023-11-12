package com.challenge.modelo_de_datos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//El manejo de roles en token se hace aqui
//clase para filtrar los request con el token
@Component
public class JWTAuthorizationFilter  extends OncePerRequestFilter {
    @Autowired
    private  UserDetailServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtener token
        String bearerToken= request.getHeader("Authorization");
        if (bearerToken!= null && bearerToken.startsWith("Bearer ")){
            //limpiar inicio de token
            String token = bearerToken.replace("Bearer ","");
            //desencriptar token
            UsernamePasswordAuthenticationToken usernamePAT= TokensUtils.getAuthentication(token);
            //obtener datos del token mandando el correo electronico del token
            UserDetails userDetails=userDetailsService.loadUserByUsername(usernamePAT.getName().toString());
            //crear nueva autentificacion, incluyendo roles
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
            //mandar autenticificacion
            //SecurityContextHolder.getContext().setAuthentication(usernamePAT);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}

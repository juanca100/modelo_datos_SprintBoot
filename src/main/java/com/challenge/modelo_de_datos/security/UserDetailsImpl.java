package com.challenge.modelo_de_datos.security;

import com.challenge.modelo_de_datos.model.Rol;
import com.challenge.modelo_de_datos.model.RolUsuario;
import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.repository.RolUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//clase para sobrecargar los detalles del usuario de sprint boot(correo,contraseña y roles)
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(rol->new SimpleGrantedAuthority("ROLE_".concat(rol.getRol())))
                .collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public  String getNombre(){
        return usuario.getNombre();
    }
}

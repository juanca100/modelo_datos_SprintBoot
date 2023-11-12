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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<Rol> roles = usuario.getRoles();

        for (Rol rol : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+rol.getRol()));
           // System.out.println(rol.getRol());
        }
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

package com.challenge.modelo_de_datos.security;

import com.challenge.modelo_de_datos.model.Usuario;
import com.challenge.modelo_de_datos.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override//Recuperar el usuario con todos sus datos de la base de datos
    public UserDetails loadUserByUsername(String correo_electronico) throws UsernameNotFoundException {
        Usuario usuario =usuarioRepository
                .findOneByEmail(correo_electronico)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario con el correo " + correo_electronico + "no existe"));
        LOG.debug("Localizando usuario "+usuario.getNombre());
        return new UserDetailsImpl(usuario);
    }
}

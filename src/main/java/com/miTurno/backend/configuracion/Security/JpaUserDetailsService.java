package com.miTurno.backend.configuracion.Security;

import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService {


    private final UsuarioRepositorio usuarioRepositorio;

    public JpaUserDetailsService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepositorio.findByCredencialEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Email no encontrado"));
    }
}

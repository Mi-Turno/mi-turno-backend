package com.miTurno.backend.configuracion.Security;
import com.miTurno.backend.data.repositorio.UsuarioRepositorio;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

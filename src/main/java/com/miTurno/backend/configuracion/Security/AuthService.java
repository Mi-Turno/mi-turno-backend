package com.miTurno.backend.configuracion.Security;

import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.request.UsuarioLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepositorio usuarioRepositorio;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UsuarioRepositorio usuarioRepositorio, AuthenticationManager authenticationManager) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.authenticationManager = authenticationManager;
    }

    public UsuarioEntidad authenticate(UsuarioLoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return usuarioRepositorio.findByCredencialEmail(input.getEmail()).orElseThrow(()-> new UsernameNotFoundException("Email no encontrado"));
    }
}

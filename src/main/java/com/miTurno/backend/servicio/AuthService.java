package com.miTurno.backend.servicio;

import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.repositorio.UsuarioRepositorio;
import com.miTurno.backend.data.dtos.request.UsuarioLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
        try {
            // Autentica las credenciales utilizando el AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );

            // Busca al usuario en la base de datos por su email
            return usuarioRepositorio.findByCredencialEmail(input.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Email no fue encontrado en el sistema"));

        } catch (BadCredentialsException ex) {
            // Si las credenciales son incorrectas, lanza una excepción personalizada
            throw new BadCredentialsException("Correo o contraseña incorrectos", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error en el proceso de autenticación", ex);
        }

    }
}

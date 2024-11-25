package com.miTurno.backend.auth;

import com.miTurno.backend.configuracion.security.jwt.JwtService;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.model.Usuario;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.request.UsuarioLoginRequest;
import com.miTurno.backend.request.UsuarioRequest;

import com.miTurno.backend.servicio.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class AuthService {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepositorio usuarioRepositorio;

    public AuthService(UsuarioService usuarioService, UsuarioMapper usuarioMapper, JwtService jwtService, AuthenticationManager authenticationManager, UsuarioRepositorio usuarioRepositorio) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public AuthResponse login(UsuarioLoginRequest usuarioLoginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioLoginRequest.getEmail(), usuarioLoginRequest.getPassword()));
        Usuario usuario = usuarioMapper.toModel(usuarioRepositorio.findByCredencialEmail(usuarioLoginRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"))) ;
        String token = jwtService.getToken(usuario);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(UsuarioRequest usuarioRequest) {
        Usuario usuario =  usuarioService.crearUnUsuario(usuarioMapper.toModel(usuarioRequest));

         return AuthResponse.builder()
                 .token(jwtService.getToken(usuario))
                 .build();

    }
}

package com.miTurno.backend.auth;

import com.miTurno.backend.configuracion.security.jwt.JwtService;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.model.Usuario;
import com.miTurno.backend.request.UsuarioLoginRequest;
import com.miTurno.backend.request.UsuarioRequest;

import com.miTurno.backend.servicio.UsuarioService;
import org.springframework.stereotype.Service;

@Service

public class AuthService {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;


    public AuthService(UsuarioService usuarioService, UsuarioMapper usuarioMapper, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.jwtService = jwtService;
    }

    public AuthResponse login(UsuarioLoginRequest usuarioLoginRequest) {
        return new AuthResponse("OK");
    }

    public AuthResponse register(UsuarioRequest usuarioRequest) {
        Usuario usuario =  usuarioService.crearUnUsuario(usuarioMapper.toModel(usuarioRequest));

         return AuthResponse.builder()
                 .token(jwtService.getToken(usuario))
                 .build();

    }
}

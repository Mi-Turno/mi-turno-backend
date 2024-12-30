package com.miTurno.backend.configuracion.security;
import org.springframework.security.core.userdetails.UserDetails;



public interface JwtService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}

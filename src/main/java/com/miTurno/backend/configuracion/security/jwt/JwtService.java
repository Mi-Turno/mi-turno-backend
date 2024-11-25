package com.miTurno.backend.configuracion.security.jwt;


import com.miTurno.backend.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    private static final String SECRET_KEY = "D2B31AA07A9078BCF5FCF7E2FBED28B16D0ACF0517FF82E23D8E1FD1F091A589";
    public String getToken(Usuario usuario) {
        return getToken(new HashMap<>(), usuario);
    }
    private String getToken(HashMap<String, Object> extraClaims, Usuario usuario) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

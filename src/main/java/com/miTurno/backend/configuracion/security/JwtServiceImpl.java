package com.miTurno.backend.configuracion.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private Key getSignInKey() {
        //Logica para obtener la clave secreta jwt, para la firma
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // - - - - - - Validaciones token - - - - - - //

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date fechaVto = extractExpiration(token);
        return fechaVto.before(new Date());
    }

    // - - - - - - Claims  - - - - - - //

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public String extractUsername(String token) {
        //extraemos el email
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        //logica para obtener todos los claims de un token jwt

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // - - - - - - Creacion de token - - - - - - //

    @Override
    public String generateToken(
            UserDetails usuarioAutenticado) {

        //agregamos extra claims como roles
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", usuarioAutenticado.getAuthorities());

        return buildToken(claims, usuarioAutenticado);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) //fecha inicio
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) //fecha vto
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)  //firma con la clave secreta
                .compact();
    }



}

package com.miTurno.backend.configuracion.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private JwtServiceImpl jwtService;

    @Autowired
    public JwtAuthenticationFilter(JwtServiceImpl jwtService, UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {


        final String authHeader = request.getHeader("Authorization");

        //Intercepción de la solicitud:
        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }
        //Extraer y validar el token:
        final String jwt = authHeader.substring(7);



        final String userEmail = jwtService.extractUsername(jwt);



        //Autenticación:
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //verificamos si tenemos el email y el usuario no esta autenticado
        if (userEmail != null && authentication == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, //se pasa null porque no tenemos las credenciales
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}


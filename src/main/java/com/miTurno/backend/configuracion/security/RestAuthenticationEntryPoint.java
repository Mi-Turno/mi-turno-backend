package com.miTurno.backend.configuracion.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

            String message = authException.getMessage();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"No autorizado\", \"mensaje\": \"" + message + "\", \"timestamp\": \"" + LocalDateTime.now() + "\"}"
            );

    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("miTurno");
        super.afterPropertiesSet();
    }
}

package com.miTurno.backend.configuracion.Security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

//            response.addHeader("WWW-Authenticate",);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"No autorizado\", \"mensaje\": \"Acceso denegado\", \"timestamp\": \"" + LocalDateTime.now() + "\"}"
            );

    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("miTurno");
        super.afterPropertiesSet();
    }
}

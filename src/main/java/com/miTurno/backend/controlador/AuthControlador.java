package com.miTurno.backend.controlador;

import com.miTurno.backend.configuracion.security.JwtServiceImpl;
import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.dtos.request.UsuarioLoginRequest;
import com.miTurno.backend.servicio.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthControlador {


    private final JwtServiceImpl jwtServiceImpl;
    private final AuthService authService;

    public AuthControlador(JwtServiceImpl jwtServiceImpl, AuthService authService) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.authService = authService;
    }

    /**Se utiliza POST para no enviar la contraseña por URL eso hace que sea mas seguro el proceso*/
    @PostMapping("/login")
    @Operation(summary = "Obtener un usuario por email y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario con los datos solicitados fue devuelto"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<String> login(
            @RequestBody UsuarioLoginRequest usuarioLoginRequest) {

        UsuarioEntidad usuarioAutenticado = authService.authenticate(usuarioLoginRequest);

        String jwtToken = jwtServiceImpl.generateToken(usuarioAutenticado);

        return ResponseEntity.ok(jwtToken); // 200 OK con usuario y credenciales
    }
}

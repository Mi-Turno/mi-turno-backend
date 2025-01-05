package com.miTurno.backend.controlador;

import com.miTurno.backend.configuracion.security.JwtServiceImpl;
import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.dtos.request.UsuarioLoginRequest;
import com.miTurno.backend.data.dtos.request.VerificarUsuarioRequest;
import com.miTurno.backend.data.repositorio.UsuarioRepositorio;
import com.miTurno.backend.servicio.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
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
    @Operation(summary = "Login de un usuario, Se necesita email y contraseña")
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

    @PostMapping("/verificar")
    @Operation(summary = "Validar codigo de verificacion enviado al email del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El mail de usuario fue validado con exito."),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<String> verificarMailUsuario(
            @RequestBody VerificarUsuarioRequest verificarUsuarioRequest) {

        String mensaje= authService.verificarUsuario(verificarUsuarioRequest);
        return  ResponseEntity.ok(mensaje);
    }

    @PostMapping("/reenviar")
    @Operation(summary = "Reenviar mail con el codigo de verificacion a un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El correo fue reenviado con exito."),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<String> reenviarMailVerificacion(
            @RequestBody String email) throws MessagingException {

        String mensaje= authService.reenviarCodigoDeVerificacion(email);
        return  ResponseEntity.ok(mensaje);
    }

}

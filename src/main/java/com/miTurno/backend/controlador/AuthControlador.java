package com.miTurno.backend.controlador;

import com.miTurno.backend.configuracion.security.JwtServiceImpl;
import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.dtos.request.CambiarContrasenia;
import com.miTurno.backend.data.dtos.request.UsuarioLoginRequest;
import com.miTurno.backend.data.dtos.request.VerificarUsuarioRequest;
import com.miTurno.backend.data.dtos.response.Usuario;
import com.miTurno.backend.data.repositorio.UsuarioRepositorio;
import com.miTurno.backend.servicio.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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




    @PostMapping("/generar-token-olvidaste-contrasenia")
    @Operation(summary = "Verifica si un usuario tiene el mail validado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Map<String,String>> postGenerarTokenOlvidasteContrasenia(
            @RequestBody String emailUsuario) throws MessagingException {

        return ResponseEntity.ok(authService.generarTokenOlvidasteContrasenia(emailUsuario));
    }

    @PostMapping("/verificar")
    @Operation(summary = "Validar codigo de verificacion enviado al email del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El mail de usuario fue validado con exito."),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<Map<String,String>> verificarMailUsuario(
            @RequestBody VerificarUsuarioRequest verificarUsuarioRequest) {


        return  ResponseEntity.ok(authService.verificarUsuario(verificarUsuarioRequest));
    }

    @PostMapping("/reenviar")
    @Operation(summary = "Reenviar mail con el codigo de verificacion a un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El correo fue reenviado con exito."),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public ResponseEntity<Map<String,String>> reenviarMailVerificacion(
            @RequestBody VerificarUsuarioRequest request) throws MessagingException {

        String mensaje= authService.reenviarCodigoDeVerificacion(request.getEmail());
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }


    @PatchMapping("/cambiar-contrasenia")
    @Operation(summary = "Cambiar la contraseña de un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La contraseña fue cambiada con exito."),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public ResponseEntity<Map<String,String>> cambiarContrasenia(
            @RequestBody CambiarContrasenia request) {
        return ResponseEntity.ok(authService.cambiarContrasenia(request.getToken(), request.getContrasenia()));
    }

}

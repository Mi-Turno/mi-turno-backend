package com.miTurno.backend.controlador;

import com.miTurno.backend.request.EmailRequest;

import com.miTurno.backend.servicio.EnviarCorreoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enviar-correo")
public class EnviarCorreoControlador {


    private final EnviarCorreoService emailService;

    public EnviarCorreoControlador(EnviarCorreoService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Enviar correo", description = "Envía un correo electrónico con los detalles proporcionados en la solicitud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo enviado exitosamente", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Bad Request)", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> enviarCorreo(@Valid @RequestBody EmailRequest emailRequest) {
        // Delegar la lógica de negocio al servicio
        emailService.EnviarCorreoService(emailRequest);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

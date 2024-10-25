package com.miTurno.backend.controlador;

import com.miTurno.backend.DTO.EmailRequest;

import com.miTurno.backend.servicio.EnviarCorreoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enviar-correo")
public class EnviarCorreoControlador {

    @Autowired
    private EnviarCorreoService emailService;

    @PostMapping
    public ResponseEntity<?> enviarCorreo(@Valid @RequestBody EmailRequest emailRequest) {
        // Delegar la lógica de negocio al servicio
        emailService.EnviarCorreoService(emailRequest);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

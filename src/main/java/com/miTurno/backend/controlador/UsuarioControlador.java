package com.miTurno.backend.controlador;


import com.miTurno.backend.DTO.UsuarioRequest;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.servicio.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioControlador(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public List<UsuarioEntidad> listarUsuarios(){
        return usuarioService.obtenerTodosLosUsuarios();
    }




}

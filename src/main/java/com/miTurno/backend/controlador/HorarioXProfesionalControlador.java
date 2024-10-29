package com.miTurno.backend.controlador;


import com.miTurno.backend.DTO.HorarioXProfesionalRequest;
import com.miTurno.backend.DTO.UsuarioLoginRequest;
import com.miTurno.backend.DTO.UsuarioRequest;
import com.miTurno.backend.entidad.HorarioXProfesionalEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.mapper.HorarioXProfesionalMapper;
import com.miTurno.backend.modelo.HorarioXProfesional;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.servicio.HorarioXProfesionalService;
import com.miTurno.backend.tipos.DiasEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/horarios-profesionales")
public class HorarioXProfesionalControlador {
    //atributos
    private final HorarioXProfesionalService horarioXProfesionalService;
    private final HorarioXProfesionalMapper horarioXProfesionalMapper;

    //constructores

    @Autowired
    public HorarioXProfesionalControlador(HorarioXProfesionalService horarioXProfesionalService, HorarioXProfesionalMapper horarioXProfesionalMapper) {
        this.horarioXProfesionalService = horarioXProfesionalService;
        this.horarioXProfesionalMapper = horarioXProfesionalMapper;
    }
    @Operation(summary = "Crear un nuevo horario para un profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "horario creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del horario inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"horario\": \"no puede estar vacío\" }"))),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorarioXProfesional crearUnHorario(@Parameter(description = "Datos del horario")
                                  @Valid @RequestBody HorarioXProfesionalRequest horarioXProfesionalRequest) {

        return horarioXProfesionalService.crearUnHorarioXProfesional(horarioXProfesionalMapper.toModel(horarioXProfesionalRequest));
    }

    @GetMapping("/profesional/dia")
    public ResponseEntity<List<HorarioXProfesionalEntidad>> obtenerHorariosPorProfesionalYDia(
            @RequestParam Long idProfesional,
            @RequestParam DiasEnum dia) {

        List<HorarioXProfesionalEntidad> horarios = horarioXProfesionalService.obtenerHorariosPorProfesionalYDia(idProfesional, dia);
        return ResponseEntity.ok(horarios);
    }


}

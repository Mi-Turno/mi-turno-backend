package com.miTurno.backend.controlador;

import com.miTurno.backend.DTO.HorarioProfesional;
import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.mapper.HorarioProfesionalMapper;
import com.miTurno.backend.request.HorarioProfesionalRequest;
import com.miTurno.backend.servicio.HorarioProfesionalService;
import com.miTurno.backend.tipos.DiasEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/negocios/{idNegocio}/profesionales/{idProfesional}/horarios")
public class HorarioProfesionalControlador {

    private final HorarioProfesionalService horarioProfesionalService;
    private final HorarioProfesionalMapper horarioProfesionalMapper;

    public HorarioProfesionalControlador(HorarioProfesionalService horarioProfesionalService, HorarioProfesionalMapper horarioProfesionalMapper) {
        this.horarioProfesionalService = horarioProfesionalService;
        this.horarioProfesionalMapper = horarioProfesionalMapper;
    }

    /*@Operation(summary = "Crear un nuevo horario para un profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "horario creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del horario inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"horario\": \"no puede estar vacío\" }"))),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HorarioProfesional> crearUnHorario(@Parameter(description = "Datos del horario")
                                              @Valid @RequestBody HorarioProfesionalRequest horarioXProfesionalRequest) {

        return horarioProfesionalService.crearUnHorarioXProfesional(horarioProfesionalMapper.toModel(horarioXProfesionalRequest));
    }

    @GetMapping("/dia/{idDia}")
    public ResponseEntity<List<HorarioProfesionalEntidad>> obtenerHorariosPorProfesionalYDia(
            @RequestParam Long idProfesional,
            @RequestParam DiasEnum dia) {

        //TODO CAMBIARLO AL PATHVARIABLE
        List<HorarioProfesionalEntidad> horarios = horarioProfesionalService.obtenerHorariosPorProfesionalYDia(idProfesional, dia);
        return ResponseEntity.ok(horarios);
    }*/


    /*@Operation(summary = "Eliminar un horario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El horario fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El horario no fue encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorarioPorId(@PathVariable Long id){
        Boolean respuesta = horarioProfesionalService.eliminarHorarioPorProfesional(id);
        if(respuesta){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
        }
    }*/


}

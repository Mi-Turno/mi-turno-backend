package com.miTurno.backend.controlador;


import com.miTurno.backend.DTO.TurnoRequest;
import com.miTurno.backend.entidad.TurnoEntidad;
import com.miTurno.backend.mapper.TurnoMapper;
import com.miTurno.backend.modelo.Turno;
import com.miTurno.backend.servicio.TurnoService;
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


@RestController
@RequestMapping("/turnos")
public class TurnoControlador {

    private final TurnoService turnoService;
    private final TurnoMapper turnoMapper;

    @Autowired
    public TurnoControlador(TurnoService turnoService, TurnoMapper turnoMapper) {
        this.turnoService = turnoService;
        this.turnoMapper = turnoMapper;
    }
    //GET
    @Operation(summary = "Obtener todos los turnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de turnos obtenida"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<TurnoEntidad> listarTurnos(){
        return turnoService.obtenerTodosLosTurnos();
    }

    //POST
    @Operation(summary = "Crear un nuevo turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del Turno inválidos", content = @Content(schema =
            @Schema(implementation = java.util.Map.Entry.class), examples = @ExampleObject(value = "{ \"fecha\": \"no puede estar vacío\" }")))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turno crearUnTurno(@Parameter(description = "Datos del turno")
                                           @Valid @RequestBody TurnoRequest turnoRequest){
        return turnoService.crearUnTurno(turnoMapper.toModel(turnoRequest));
    }

    //DELETE
    @Operation(summary = "Eliminar un Turno por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El turno fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El turno no fue encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurnoPorId(Long id){
        Boolean respuesta = turnoService.eliminarTurnoPorId(id);
        if(respuesta){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
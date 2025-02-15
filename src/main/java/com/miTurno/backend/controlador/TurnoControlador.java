package com.miTurno.backend.controlador;


import com.miTurno.backend.data.dtos.request.TurnoRequest;
import com.miTurno.backend.data.dtos.response.Turno;
import com.miTurno.backend.servicio.TurnoService;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
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
@RequestMapping("/negocios/{idNegocio}/turnos")
public class TurnoControlador {



    private final TurnoService turnoService;


    @Autowired
    public TurnoControlador(TurnoService turnoService) {
        this.turnoService = turnoService;
    }



    //GET x id negocio
    @Operation(summary = "Obtener todos los turnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de turnos obtenida"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<Turno> listarTurnosPorNegocio(@PathVariable Long idNegocio){
        return turnoService.obtenerTodosLosTurnosPorNegocio(idNegocio);
    }
    //GET x id
    @Operation(summary = "Obtener turno por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de turnos obtenida"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{turnoId}")
    public Turno obtenerTurnoPorId(@PathVariable Long turnoId){
        return turnoService.obtenerTurnoPorId(turnoId);
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
                                           @Valid @RequestBody TurnoRequest turnoRequest,
                              @PathVariable Long idNegocio){

        return turnoService.crearUnTurno(turnoRequest,idNegocio);
    }

    //DELETE
@Operation(summary = "Eliminar un Turno por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El turno fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El turno no fue encontrado")
    })
    @DeleteMapping("/{idTurno}")
    public ResponseEntity<Void> eliminarTurnoPorId(@PathVariable Long idNegocio,@PathVariable Long idTurno){
        Boolean respuesta = turnoService.eliminarTurnoPorId(idNegocio,idTurno);
        if(respuesta != null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modificar un turno por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "El turno fue modificado con exito"),
            @ApiResponse(responseCode = "404", description = "El turno no fue encontrado")
    })
    @PatchMapping("/{idTurno}/{estadoTurnoEnum}")
    public ResponseEntity<Turno> modificarTurnoPorId(@PathVariable Long idNegocio, @PathVariable Long idTurno, @PathVariable EstadoTurnoEnum estadoTurnoEnum){
        Turno turno = turnoService.modificarTurnoPorId(idNegocio,idTurno,estadoTurnoEnum);
        if(turno != null){
            return new ResponseEntity<>(turno,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}

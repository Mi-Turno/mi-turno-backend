package com.miTurno.backend.controlador;

import com.miTurno.backend.data.dtos.response.HorarioProfesional;
import com.miTurno.backend.data.mapper.HorarioProfesionalMapper;
import com.miTurno.backend.data.dtos.request.HorarioProfesionalRequest;
import com.miTurno.backend.servicio.HorarioProfesionalService;
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

    //todo este post esta indocumentado
    @Operation(summary = "Crear un nuevo horario para un profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "horario creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del horario inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"horario\": \"no puede estar vacío\" }"))),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HorarioProfesional> crearUnHorario(@Parameter(description = "Datos del horario")
                                              @Valid @RequestBody HorarioProfesionalRequest horarioXProfesionalRequest,
                                                             @PathVariable Long idNegocio,
                                                             @PathVariable Long idProfesional) {

        HorarioProfesional horarioProfesional=horarioProfesionalService.crearUnHorarioXProfesional(idNegocio,idProfesional,horarioXProfesionalRequest);
        return ResponseEntity.ok(horarioProfesional);
    }

    @Operation(summary = "Modificar estado de un horario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horario modificado con exito"),
            @ApiResponse(responseCode = "400", description = "Datos del horario inválidos"),
    })
    @PatchMapping("/modificar/{idHorario}") //ya existe un /{id} entonces agrego el /modificar
    public ResponseEntity<HorarioProfesional> modificarEstadoHorario(
            @PathVariable Long idHorario,
            @RequestBody @Parameter(description = "Estado del horario a modificar")
            @Schema(example = "{\"estado\": true}") Map<String, Boolean> estadoMap) {
        boolean estado = estadoMap.get("estado");
        HorarioProfesional horarioProfesional = horarioProfesionalService.modificarEstadoHorario(idHorario, estado);
        return ResponseEntity.ok(horarioProfesional);
    }


    @Operation(summary = "Obtener listado de horarios de un profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Horarios obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<HorarioProfesional> obtenerListadoDeHorariosDeUnProfesional(@PathVariable Long idNegocio,@PathVariable Long idProfesional){


        return horarioProfesionalMapper.toModelList(horarioProfesionalService.obtenerListadoDeHorariosDeUnProfesional(idNegocio,idProfesional));
    }


    //GET horarios del profesional por dia
    @GetMapping("/dia/{idDia}")
    @Operation(summary = "Obtener listado de horarios de un profesional por dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Horarios del profesional obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    public ResponseEntity<List<HorarioProfesional>> obtenerHorariosPorProfesionalYDia(
            @PathVariable Long idProfesional,
            @PathVariable Long idNegocio,
            @PathVariable Long idDia) {


        List<HorarioProfesional> horarios = horarioProfesionalMapper.toModelList(horarioProfesionalService.obtenerHorariosPorProfesionalYDia(idNegocio,idProfesional,idDia));
        return ResponseEntity.ok(horarios);
    }

    //GET un horarioProfesional por id
    @GetMapping("/{idHorarioProfesional}")
    @Operation(summary = "Obtener listado de horarios de un profesional por dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Horarios del profesional obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    public ResponseEntity<HorarioProfesional> obtenerHorarioProfesionalPorId(
            @PathVariable Long idProfesional,
            @PathVariable Long idNegocio,
            @PathVariable Long idHorarioProfesional) {


        HorarioProfesional horarioProfesional = horarioProfesionalMapper.toModel(horarioProfesionalService.obtenerHorarioProfesionalPorId(idNegocio,idProfesional,idHorarioProfesional));
        return ResponseEntity.ok(horarioProfesional);
    }


    @Operation(summary = "Eliminar un horario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El horario fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El horario no fue encontrado")
    })
    @DeleteMapping("/{idHorario}")
    public ResponseEntity<Void> eliminarHorarioPorId(@PathVariable Long idHorario){
        Boolean respuesta = horarioProfesionalService.eliminarHorarioPorProfesional(idHorario);
        if(respuesta){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
        }
    }


}

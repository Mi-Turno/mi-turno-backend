package com.miTurno.backend.controlador;

import com.miTurno.backend.model.Profesional;
import com.miTurno.backend.model.Turno;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.servicio.ProfesionalService;
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

@RestController()
@RequestMapping("/negocios/{idNegocio}/profesionales")
public class ProfesionalControlador {
    private final ProfesionalService profesionalService;
    private final ProfesionalMapper profesionalMapper;

    public ProfesionalControlador(ProfesionalService profesionalService, ProfesionalMapper profesionalMapper) {
        this.profesionalService = profesionalService;
        this.profesionalMapper = profesionalMapper;
    }


    //GET todos los profesionales x id negocio
    @Operation(summary = "Obtener profesionales por id negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<Profesional> obtenerProfesionalesPorIdNegocio(@PathVariable Long idNegocio){
        return profesionalService.obtenerProfesionalesPorIdNegocio(idNegocio);
    }

    //GET profesional x id ("/{idProfesional}")obtenerUnProfesional
    @Operation(summary = "Obtener profesionales por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Profesional obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{idProfesional}")
    public Profesional obtenerProfesionalPorId(@PathVariable Long idNegocio,@PathVariable Long idProfesional){
        return profesionalService.obtenerUnProfesional(idProfesional);
    }
    //GET listado de turnos agendados del profesional ("/{idProfesional}/turnos")

    @Operation(summary = "Obtener turnos de profesional por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Profesional obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{idProfesional}/turnos")
    public List<Turno> obtenerTurnosProfesionalPorId(@PathVariable Long idNegocio, @PathVariable Long idProfesional){
        return profesionalService.obtenerTurnosProfesionalPorIdNegocioYIdProfesional(idNegocio,idProfesional);
    }

    // GET todos los profesionales por id negocio y estado
    @Operation(summary = "Obtener profesionales por id de negocio y estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "profesionales obtenidos con éxito"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "404", description = "No se encontraron profesionales para el negocio y estado especificados")
    })
    @GetMapping("/estado/{estado}")
    public List<Profesional> obtenerProfesionalesPorIdNegocioYEstado(@PathVariable Long idNegocio, @PathVariable String estado) {
        Boolean estadoBooleano = Boolean.valueOf(estado);  // Convierte "true" o "false" a Boolean

        return profesionalService.obtenerServiciosPorIdNegocioYEstado(idNegocio, estadoBooleano);
    }


    //POST profesional por negocio
    @Operation(summary = "Crear un nuevo profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "profesional creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del profesional inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"nombre\": \"no puede estar vacío\" }"))),
            @ApiResponse(responseCode = "409", description = "El email o telefono ingresado ya existe")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Profesional crearUnProfesionalPorNegocio(@Parameter(description = "Datos del profesional")
                                                @Valid @RequestBody ProfesionalRequest profesionalRequest,
                                                @PathVariable Long idNegocio) {


        return profesionalService.crearUnprofesional(idNegocio, profesionalRequest);
    }

    @Operation(
            summary = "Asignar un servicio a un profesional",
            description = "Este endpoint permite asignar un servicio existente a un profesional dentro de un negocio. Actualiza la relación entre ambos recursos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio asignado correctamente al profesional"),
            @ApiResponse(responseCode = "404", description = "Negocio, profesional o servicio no encontrados"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{idProfesional}/servicios/{idServicio}")
     public ResponseEntity<Profesional> asignarUnServicio(@PathVariable Long idNegocio,@PathVariable Long idProfesional,@PathVariable Long idServicio){
        return ResponseEntity.ok(profesionalService.asignarUnServicio(idProfesional,idServicio));
     }


    //UPDATE - PUT
    @Operation(summary = "Actualiza un profesional por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Profesional actualizado con exito"),
            @ApiResponse(responseCode = "404",description = "Profesional no encontrado")
    })
    @PutMapping("/{idProfesional}")
    public Profesional actualizarServicio (
            @Parameter(description = "ID del Servicio para actualizar",example = "1")
            @PathVariable Long idNegocio,
            @Parameter(description = "Datos actualizado del Profesional")
            @PathVariable Long idProfesional,
            @RequestBody ProfesionalRequest profesionalRequest) throws ServicioNoExisteException {
        return profesionalService.actualizarProfesional(idNegocio,idProfesional,profesionalMapper.toModel(profesionalRequest));
    }

    //DELETE un profesional x id ("/{idProfesional}")

    //DELETE
    @Operation(summary = "Eliminar un Profesional por id")
    @DeleteMapping("/{idProfesional}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El Profesional fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El Profesional no fue encontrado")
    })
    public Boolean eliminarProfesional(@PathVariable Long idNegocio,@PathVariable Long idProfesional) throws ServicioNoExisteException {
        return profesionalService.eliminarUnProfesional(idNegocio,idProfesional);
    }

}

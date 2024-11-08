package com.miTurno.backend.controlador;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.DTO.Servicio;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.request.ServicioRequest;
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
    public Profesional obtenerProfesionalPorId(@PathVariable Long idProfesional){
        return profesionalService.obtenerUnProfesional(idProfesional);
    }
    //GET listado de turnos agendados del profesional ("/{idProfesional}/turnos")

    //GET listado de horarios del profesional ("/{idProfesional}/horarios")

    //GET listado de servicios que ofrece un profesional ("/{idProfesional}/servicios")

    //POST un profesional

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




    //POST nuevo turno a un profesional ("/{idProfesional}/turnos")

    //POST nuevo servicio a un profesional ("/{idProfesional}/servicios") (el servicio debe encontrarse en el negocio en el que esta el profesional)

    //POST nuevo horario que ofrece un profesional ("/{idProfesional}/horarios")

    //UPDATE un profesional x id ("/{idProfesional}")

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



}

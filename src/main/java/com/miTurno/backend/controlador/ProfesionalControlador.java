package com.miTurno.backend.controlador;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.DTO.Usuario;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/negocios/{idNegocio}/profesionales")
public class ProfesionalControlador {
    private final ProfesionalService profesionalService;

    public ProfesionalControlador(ProfesionalService profesionalService) {
        this.profesionalService = profesionalService;
    }


    //GET todos los profesionales x id negocio
    /*@Operation(summary = "Obtener profesionales por id negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<Profesional> obtenerProfesionalesPorIdNegocio(@PathVariable Long idNegocio){
        return profesionalService.obtenerProfesionalesPorIdNegocio(idNegocio);
    }*/

    //GET profesional x id ("/{idProfesional}")

    //GET listado de turnos agendados del profesional ("/{idProfesional}/turnos")

    //GET listado de horarios del profesional ("/{idProfesional}/horarios")

    //GET listado de servicios que ofrece un profesional ("/{idProfesional}/servicios")

    //POST un profesional

    //POST profesional por negocio
    /*@Operation(summary = "Crear un nuevo profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "profesional creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del profesional inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"nombre\": \"no puede estar vacío\" }"))),
            @ApiResponse(responseCode = "409", description = "El email o telefono ingresado ya existe")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crearUnProfesionalPorNegocio(@Parameter(description = "Datos del profesional")
                                                @Valid @RequestBody ProfesionalRequest profesionalRequest,
                                                @PathVariable Long idNegocio) {

        //todo CAMBIAR A PROFESIONALES SERVICE
        return profesionalService.crearUnprofesional(profesionalRequest);
    }*/


    //POST nuevo turno a un profesional ("/{idProfesional}/turnos")

    //POST nuevo servicio a un profesional ("/{idProfesional}/servicios") (el servicio debe encontrarse en el negocio en el que esta el profesional)

    //POST nuevo horario que ofrece un profesional ("/{idProfesional}/horarios")

    //UPDATE un profesional x id ("/{idProfesional}")

    //DELETE un profesional x id ("/{idProfesional}")



}

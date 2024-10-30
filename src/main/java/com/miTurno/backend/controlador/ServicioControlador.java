package com.miTurno.backend.controlador;

import com.miTurno.backend.DTO.ServicioRequest;
import com.miTurno.backend.DTO.UsuarioRequest;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.mapper.ServicioMapper;
import com.miTurno.backend.modelo.Servicio;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.servicio.ServicioService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
public class ServicioControlador {
    private final ServicioService servicioService;
    private final ServicioMapper servicioMapper;

    @Autowired
    public ServicioControlador(ServicioService servicioService, ServicioMapper servicioMapper) {
        this.servicioService = servicioService;
        this.servicioMapper = servicioMapper;
    }

    //GET todos los servicios

    @Operation(summary = "Obtener todos los servicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de servicios obtenida exitosamente"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    public List<ServicioEntidad> listarTodosLosServicios(){
        return servicioService.obtenerListadoTodosLosServicios();
    }

    //GET servicios x criterio
    @Operation(summary = "Obtener servicios por un criterio especifico(nombreServicio,precio o duracion)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de servicios obtenida exitosamente"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<Servicio> listarServicios(
            @Parameter(description = "Filtro opcional para buscar servicios por nombre", example = "corte con maquina")
            @RequestParam(required = false) String nombreServicio,
            @Parameter(description = "Filtro opcional para buscar servicios por precio", example = "2500")
            @RequestParam(required = false) Boolean estado

    ){
        return servicioService.obtenerListadoServicios(nombreServicio, estado);
    }







    //POST
    @Operation(summary = "Crear un nuevo Servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Servicio creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del servicio inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"precio\": \"no puede estar vacío, tampoco puede ser menor a 0\" }")))
    })
    @PostMapping
    public Servicio crearUnServicio(
            @Valid @RequestBody ServicioRequest servicioRequest
            ){
        return servicioService.crearUnServicio(servicioMapper.toModel(servicioRequest));
    }


    //todo POST asignar servicio a un profesional



    //DELETE
    @Operation(summary = "Eliminar un servicio por id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El servicio fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El servicio no fue encontrado")
    })
    public Boolean eliminarUnServicio(@PathVariable Long id) throws ServicioNoExisteException {
        return servicioService.eliminarUnServicio(id);
    }

    //UPDATE

    @Operation(summary = "Actualiza un servicio por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Servicio actualizado con exito"),
            @ApiResponse(responseCode = "404",description = "Servicio no encontrado")
    })
    @PutMapping("/{id}")
    public Servicio actualizarServicio (
            @Parameter(description = "ID del Servicio para actualizar",example = "4")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizado del Servicio")
            @RequestBody ServicioRequest servicioRequest) throws ServicioNoExisteException{

        return servicioService.actualizarUnServicio(id,servicioMapper.toModel(servicioRequest));
    }
}

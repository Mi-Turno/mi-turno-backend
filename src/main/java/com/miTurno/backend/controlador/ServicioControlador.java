package com.miTurno.backend.controlador;

import com.miTurno.backend.DTO.Servicio;
import com.miTurno.backend.request.ServicioRequest;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.mapper.ServicioMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/negocios/{idNegocio}/servicios")
public class ServicioControlador {
    private final ServicioService servicioService;
    private final ServicioMapper servicioMapper;

    @Autowired
    public ServicioControlador(ServicioService servicioService, ServicioMapper servicioMapper) {
        this.servicioService = servicioService;
        this.servicioMapper = servicioMapper;
    }

    //GET todos los servicios

   /* @Operation(summary = "Obtener todos los servicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de servicios obtenida exitosamente"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/negocios/{idNegocio}")
    public List<ServicioEntidad> listarTodosLosServicios(@PathVariable Long idNegocio){
        return servicioService.obtenerListadoTodosLosServiciosPorNegocio(idNegocio);
    }*/

    //POST servicio por negocio /negocios/{idNegocio}/servicios

    //GET servicios x criterio
//    @Operation(summary = "Obtener servicios por un criterio especifico(nombreServicio,precio o duracion)")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",description = "Lista de servicios obtenida exitosamente"),
//            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
//    })
//    @GetMapping
//    public List<Servicio> listarServicios(
//            @Parameter(description = "Filtro opcional para buscar servicios por nombre", example = "corte con maquina")
//            @RequestParam(required = false) String nombreServicio,
//            @Parameter(description = "Filtro opcional para buscar servicios por precio", example = "2500")
//            @RequestParam(required = false) Boolean estado
//
//    ){
//        return servicioService.obtenerListadoServicios(nombreServicio, estado);
//    }

//GET x id
    @Operation(summary = "Obtener servicio por id y por negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de servicios obtenida exitosamente"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{idServicio}")
    public Servicio obtenerServicioPorId(@PathVariable Long idNegocio, @PathVariable Long idServicio){
        return servicioService.obtenerUnServicioPorIdYPorIdNegocio(idNegocio, idServicio);
    }
//GET listado x id negocio

    @Operation(summary = "Obtener servicio por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de servicios obtenida exitosamente"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<Servicio> obtenerListadoDeServiciosPorIdNegocio(@PathVariable Long idNegocio){
        return servicioService.obtenerListadoDeServiciosPorIdNegocio(idNegocio);
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
            @Valid @RequestBody ServicioRequest servicioRequest,
            @PathVariable Long idNegocio
            ){
        return servicioService.crearUnServicio(idNegocio,servicioRequest);
    }






    //DELETE
    @Operation(summary = "Eliminar un servicio por id")
    @DeleteMapping("/{idServicio}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El servicio fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El servicio no fue encontrado")
    })
    public Boolean eliminarUnServicio(@PathVariable Long idNegocio,@PathVariable Long idServicio) throws ServicioNoExisteException {
        return servicioService.eliminarUnServicio(idNegocio,idServicio);
    }

    //UPDATE

    @Operation(summary = "Actualiza un servicio por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Servicio actualizado con exito"),
            @ApiResponse(responseCode = "404",description = "Servicio no encontrado")
    })
    @PutMapping("/{idServicio}")
    public Servicio actualizarServicio (
            @Parameter(description = "ID del Servicio para actualizar",example = "1")
            @PathVariable Long idNegocio,
            @Parameter(description = "Datos actualizado del Servicio")
            @PathVariable Long idServicio,
            @RequestBody ServicioRequest servicioRequest) throws ServicioNoExisteException{

        return servicioService.actualizarUnServicio(idNegocio,idServicio,servicioMapper.toModel(servicioRequest));
    }
}

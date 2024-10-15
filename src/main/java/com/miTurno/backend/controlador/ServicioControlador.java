package com.miTurno.backend.controlador;

import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.servicio.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicios")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ServicioControlador {
    private final ServicioService servicioService;

    @Autowired
    public ServicioControlador(ServicioService servicioService) {
        this.servicioService = servicioService;
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
    public List<ServicioEntidad> listarServicios(
            @Parameter(description = "Filtro opcional para buscar servicios por nombre", example = "corte con maquina")
            @RequestParam(required = false) String nombreServicio,
            @Parameter(description = "Filtro opcional para buscar servicios por precio", example = "2500")
            @RequestParam(required = false) Double precio,
            @Parameter(description = "Filtro opcional para buscar servicios por duracion", example = "2500")
            @RequestParam(required = false) String duracion
    ){
        return servicioService.obtenerListadoServicios(nombreServicio,precio,duracion);
    }

}

package com.miTurno.backend.controlador;

import com.miTurno.backend.data.domain.NegocioEntidad;
import com.miTurno.backend.data.dtos.request.NegocioRequest;
import com.miTurno.backend.data.dtos.response.Negocio;
import com.miTurno.backend.servicio.NegocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/negocios")
public class NegocioControlador {
    //atributos

    private final NegocioService negocioService;


    //constructores
    @Autowired
    public NegocioControlador( NegocioService negocioService) {
        this.negocioService = negocioService;
    }

    //POST negocio
   @Operation(summary = "Crear un nuevo negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "negocio creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del negocio inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"nombre\": \"no puede estar vacío\" }"))),
            @ApiResponse(responseCode = "409", description = "El email,telefono o nombre del negocio ingresado ya existe")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Negocio crearUnNegocio(@Parameter(description = "Datos del negocio")
                                          @Valid @RequestBody NegocioRequest negocioRequest) throws MessagingException {

        return negocioService.crearUnNegocio(negocioRequest);
    }


    //GET all
    @Operation(summary = "Obtener todos los negocios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de negocios obtenida"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<Negocio> listarNegocios(){
        return negocioService.listarTodosLosNegocios();
    }


    //GET negocio x id
    @Operation(summary = "Obtener numero del negocio por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Numero del negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/numero-soporte/{idNegocio}")
    public ResponseEntity<Long> obtenerNumeroDelNegocioPorId(@PathVariable Long idNegocio){

        Long telefono = negocioService.obtenerNumeroDeUnNegocioById(idNegocio);
        return ResponseEntity.ok(telefono);
    }

    @Operation(summary = "Obtener negocio por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/id/{idNegocio}")
    public Negocio obtenerUnNegocioPorIdNegocio(@PathVariable Long idNegocio){
        return negocioService.obtenerNegocioPorId(idNegocio);
    }

    //GET negocio x nombre
    @Operation(summary = "Obtener negocio por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/nombre/{nombreNegocio}")
    public Negocio obtenerUnNegocioPorNombreNegocio(@PathVariable String nombreNegocio){
        return negocioService.obtenerNegocioPorNombre(nombreNegocio);
    }

    //GET idnegocio x nombre
    @Operation(summary = "Obtener id negocio por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{nombreNegocio}")
    public Long obtenerIdNegocioPorNombreNegocio(@PathVariable String nombreNegocio){


        return negocioService.obtenerIdNegocioPorNombreNegocio(nombreNegocio);
    }

    //GET negocios x aproximacion de nombre
    @Operation(summary = "Obtener listado de negocios por nombre(busqueda)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocios obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("buscar/{nombreNegocio}")
    public List<NegocioEntidad> obtenerNegociosPorNombreParecido(@PathVariable String nombreNegocio){


        return negocioService.obtenerListadoDeNegociosConNombreAproximado(nombreNegocio);
    }


}

package com.miTurno.backend.controlador;

import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.DTO.Negocio;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.servicio.NegocioService;
import com.miTurno.backend.servicio.UsuarioService;
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

@RestController()
@RequestMapping("/negocios")
public class NegocioControlador {
    //atributos

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final NegocioService negocioService;


    //constructores
    @Autowired
    public NegocioControlador(UsuarioService usuarioService, UsuarioMapper usuarioMapper, NegocioService negocioService) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NegocioEntidad crearUnNegocio(@Parameter(description = "Datos del negocio")
                                          @Valid @RequestBody NegocioRequest negocioRequest) {

        return negocioService.crearUnNegocio(negocioRequest);
    }



    //GET all
    @Operation(summary = "Obtener todos los negocios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de negocios obtenida"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<NegocioEntidad> listarNegocios(){
        return negocioService.listarTodosLosNegocios();
    }


    //GET negocio x id
    @Operation(summary = "Obtener negocio por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{idNegocio}")
    public NegocioEntidad obtenerUnNegocioPorIdNegocio(@PathVariable Long idNegocio){
        return negocioService.obtenerNegocioPorId(idNegocio);
    }

    //GET servicios x id negocio



    //GET profesional especifico x id x negocio

    //GET servicio especifico x id x negocio




}

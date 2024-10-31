package com.miTurno.backend.controlador;

import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.DTO.Negocio;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.servicio.NegocioService;
import com.miTurno.backend.servicio.ProfesionalesXNegocioService;
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
    private final ProfesionalesXNegocioService profesionalesXNegocioService;
    private final NegocioService negocioService;


    //constructores
    @Autowired
    public NegocioControlador(UsuarioService usuarioService, UsuarioMapper usuarioMapper, ProfesionalesXNegocioService profesionalesXNegocioService, NegocioService negocioService) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.profesionalesXNegocioService = profesionalesXNegocioService;
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
    public Negocio crearUnNegocio(@Parameter(description = "Datos del negocio")
                                          @Valid @RequestBody NegocioRequest negocioRequest) {

        //todo CAMBIAR
        return negocioService.crearUnNegocio(negocioRequest);
    }

    //POST profesional por negocio
    @Operation(summary = "Crear un nuevo profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "profesional creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del profesional inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"nombre\": \"no puede estar vacío\" }"))),
            @ApiResponse(responseCode = "409", description = "El email o telefono ingresado ya existe")
    })
    @PostMapping("{idNegocio}/profesionales")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crearUnProfesionalPorNegocio(@Parameter(description = "Datos del profesional")
                                                @Valid @RequestBody ProfesionalRequest profesionalRequest,
                                                @PathVariable Long idNegocio) {

        //todo CAMBIAR A PROFESIONALES SERVICE
        return usuarioService.crearUnprofesional(profesionalRequest);
    }





    //GET all
    @Operation(summary = "Obtener todos los negocios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de negocios obtenida"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<Usuario> listarNegocios(){
        List<UsuarioEntidad> usuarioEntidadList = usuarioService.obtenerTodosLosNegocios();
        return usuarioEntidadList.stream().map(usuarioMapper::toModel).toList();
    }


    //GET negocio x id
    @Operation(summary = "Obtener negocio por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{idNegocio}")
    public Usuario obtenerUnNegocioPorIdNegocio(@PathVariable Long idNegocio){
        UsuarioEntidad usuarioEntidad= usuarioService.obtenerNegocioPorId(idNegocio);
        return usuarioMapper.toModel(usuarioEntidad);
    }

    //GET servicios x id negocio

    //GET profesionales x id negocio
    @Operation(summary = "Obtener profesionales por id negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{idNegocio}/profesionales")
    public List<Usuario> obtenerProfesionalesPorIdNegocio(@PathVariable Long idNegocio){
       return profesionalesXNegocioService.obtenerProfesionalesPorIdNegocio(idNegocio);
    }

    //GET profesional especifico x id x negocio

    //GET servicio especifico x id x negocio




}

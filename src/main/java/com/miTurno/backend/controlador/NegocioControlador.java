package com.miTurno.backend.controlador;

import com.miTurno.backend.data.domain.NegocioEntidad;
import com.miTurno.backend.data.dtos.request.NegocioRequest;
import com.miTurno.backend.data.dtos.response.Negocio;
import com.miTurno.backend.data.dtos.response.personalizados.ClienteTablaResponse;
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


    /**GET clientes por negocio id */
    @Operation(summary = "Obtener id negocio por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio obtenido con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{idNegocio}/clientes")
    public ResponseEntity<List<ClienteTablaResponse>> obtenerClientesPorNegocio(@PathVariable Long idNegocio){

        List<ClienteTablaResponse> listaClientePorNegocio = negocioService.obtenerClientesPorNegocioId(idNegocio);
        return ResponseEntity.ok(listaClientePorNegocio);
    }
    //PUT negocio x id
    @Operation(summary = "Actualizar un negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Negocio actualizado con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @PutMapping("/{idNegocio}")
    public Negocio actualizarNegocio(@PathVariable Long idNegocio, @RequestBody NegocioRequest negocioRequest) throws MessagingException {
        return negocioService.actualizarNegocioPorID(idNegocio,negocioRequest);
    }
    @Operation(
            summary = "Agregar un método de pago al negocio",
            description = "Permite agregar un método de pago a un negocio existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Método de pago agregado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Negocio o método de pago no encontrado")
            }
    )
    @PatchMapping("/{negocioId}/metodos-pago/alta")
    public ResponseEntity<Negocio> altaMetodoPago(
            @Parameter(description = "ID del negocio", required = true)
            @PathVariable Long negocioId,
            @Parameter(description = "ID del método de pago a agregar", required = true)
            @RequestParam Long metodoDePagoId) {
        Negocio negocioActualizado = negocioService.agregarMetodoPago(negocioId, metodoDePagoId);
        return ResponseEntity.ok(negocioActualizado);
    }

    @Operation(
            summary = "Eliminar un método de pago del negocio",
            description = "Permite eliminar un método de pago de un negocio existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Método de pago eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Negocio o método de pago no encontrado")
            }
    )
    @PatchMapping("/{negocioId}/metodos-pago/baja")
    public ResponseEntity<Negocio> bajaMetodoPago(
            @Parameter(description = "ID del negocio", required = true)
            @PathVariable Long negocioId,
            @Parameter(description = "ID del método de pago a eliminar", required = true)
            @RequestParam Long metodoDePagoId) {
        Negocio negocioActualizado = negocioService.eliminarMetodoPago(negocioId, metodoDePagoId);
        return ResponseEntity.ok(negocioActualizado);
    }

}

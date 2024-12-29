package com.miTurno.backend.controlador;


import com.miTurno.backend.data.dtos.response.Cliente;
import com.miTurno.backend.data.dtos.response.Turno;
import com.miTurno.backend.data.dtos.request.UsuarioLoginRequest;
import com.miTurno.backend.data.dtos.request.UsuarioRequest;
import com.miTurno.backend.servicio.ClienteService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteControlador {

    private final ClienteService clienteService;


    @Autowired
    public ClienteControlador(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //POST cliente
    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del cliente inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"nombre\": \"no puede estar vacío\" }"))),
            @ApiResponse(responseCode = "409", description = "El email o telefono ingresado ya existe")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crearUnCliente(@Parameter(description = "Datos del cliente")
                                      @Valid @RequestBody UsuarioRequest usuarioRequest) {

        return clienteService.crearUnCliente(usuarioRequest);
    }
    //POST QUE FUNCIONA COMO GET PARA EL LOGIN
    @PostMapping("/login")
    @Operation(summary = "Obtener un cliente por email y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El cliente con los datos solicitados fue devuelto"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> obtenerClienteByEmailAndPassword(@RequestBody UsuarioLoginRequest usuarioLoginRequest) {
        Cliente cliente = clienteService.obtenerClienteByEmailAndPassword(usuarioLoginRequest.getEmail(), usuarioLoginRequest.getPassword());
        if (cliente != null) {
            return ResponseEntity.ok(cliente); // 200 OK con usuario y credenciales
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //GET listado de turnos por cliente ID

    @Operation(summary = "Obtener un listado de turnos del cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "El listado de turnos del cliente enviado con exito"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{id}/turnos")
    public ResponseEntity<List<Turno>> obtenerListadoDeTurnosDelClientePorId(@Parameter(description = "ID del cliente",example = "1")
                                                       @PathVariable Long id){
        List<Turno> listadoDeturnos= clienteService.obtenerListadoDeTurnosPorId(id);
        return ResponseEntity.ok(listadoDeturnos);//200
    }

    //GET by ID
    @Operation(summary = "Obtener un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "El cliente con el ID fue devuelto"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@Parameter(description = "ID del cliente",example = "1")
                                                       @PathVariable Long id){
        Cliente cliente= clienteService.buscarCliente(id);
        return ResponseEntity.ok(cliente);//200
    }


    //UPDATE cliente
    @Operation(summary = "actualizar usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Cliente actualizado con exito"),
            @ApiResponse(responseCode = "404",description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public Cliente actualizarUsuario(
            @Parameter(description = "ID del cliente para actualizar",example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizado del cliente")
            @RequestBody UsuarioRequest usuarioRequest){

        return clienteService.actualizarClientePorId(id,usuarioRequest);
    }


    //DELETE logico
    @Operation(summary = "Eliminar un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El cliente fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El cliente no fue encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUnClientePorId(@PathVariable Long id){
        Boolean respuesta = clienteService.eliminarClientePorId(id);
        if(respuesta){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
        }
    }

}

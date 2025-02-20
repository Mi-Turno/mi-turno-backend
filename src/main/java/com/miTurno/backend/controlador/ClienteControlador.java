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
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crearUnCliente(@Parameter(description = "Datos del cliente")
                                  @Valid @RequestBody UsuarioRequest usuarioRequest) throws MessagingException {

        return clienteService.crearUnCliente(usuarioRequest);
    }


    @Operation(summary = "Crear un nuevo cliente invitado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente invitado creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del cliente inválidos")
    })
    @PostMapping("/{nombreCliente}/invitado/{nombreNegocio}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crearUsuarioInvitado(@Parameter(description = "Nombre del cliente invitado") @PathVariable String nombreCliente, @PathVariable String nombreNegocio) {
        return clienteService.crearUsuarioInvitado(nombreCliente, nombreNegocio);
    }



    //GET listado de turnos por cliente ID

    @Operation(summary = "Obtener un listado de turnos del cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El listado de turnos del cliente enviado con exito"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos")
    })
    @GetMapping("/{id}/turnos")
    public ResponseEntity<List<Turno>> obtenerListadoDeTurnosDelClientePorId(@Parameter(description = "ID del cliente", example = "1")
                                                                             @PathVariable Long id) {
        List<Turno> listadoDeturnos = clienteService.obtenerListadoDeTurnosPorId(id);
        return ResponseEntity.ok(listadoDeturnos);//200
    }



    //GET by ID
    @Operation(summary = "Obtener un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El cliente con el ID fue devuelto"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@Parameter(description = "ID del cliente", example = "1")
                                                       @PathVariable Long id) {
        Cliente cliente = clienteService.buscarCliente(id);
        return ResponseEntity.ok(cliente);//200
    }


    @Operation(summary = "Obtener el último cliente invitado por negocio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El cliente invitado fue devuelto"),
            @ApiResponse(responseCode = "400", description = "Cliente no encontrado")
    })
    @GetMapping("/ultimo-invitado")
    public ResponseEntity<Cliente> obtenerUltimoClienteInvitadoPorNegocio(
            @Parameter(description = "Nombre del negocio", example = "NombreNegocio") @RequestParam String nombreNegocio) {
        Cliente cliente = clienteService.getLastClienteInvitadoByNegocio(nombreNegocio);
        return ResponseEntity.ok(cliente);
    }

    //GET email by id
    /**
     * @Map.of es para no tener que hacer un DTO por un valor
     * **/
    @Operation(summary = "Obtener email de un cliente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El email del cliente con el ID fue devuelto"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos")
    })
    @GetMapping("/email/{id}")
    public ResponseEntity<Map<String,String>> obtenerEmailCliente(@Parameter(description = "ID del cliente", example = "1")
                                                       @PathVariable Long id) {
        String email = clienteService.obtenerEmailPorId(id);
        return ResponseEntity.ok(Map.of("email",email));//200
    }

    //UPDATE cliente
    @Operation(summary = "actualizar usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado con exito"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public Cliente actualizarUsuario(
            @Parameter(description = "ID del cliente para actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizado del cliente")
            @RequestBody UsuarioRequest usuarioRequest) {

        return clienteService.actualizarClientePorId(id, usuarioRequest);
    }

    //UPDATE
    @Operation(summary = "Actualizar usuario de forma parcial mediante el ID")
    @PatchMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado con exito"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Cliente> actualizarClienteParcial(
            @PathVariable Long id,
            @RequestBody Cliente clienteParcial) { //todo hacer un dto correspondiente a este metodo HTTP
        try {
            Cliente clienteActualizado = clienteService.actualizarParcial(id, clienteParcial);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE logico
    @Operation(summary = "Eliminar un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "El cliente fue borrado con exito"),
            @ApiResponse(responseCode = "404", description = "El cliente no fue encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUnClientePorId(@PathVariable Long id) {
        Boolean respuesta = clienteService.eliminarClientePorId(id);
        if (respuesta) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
        }
    }

}

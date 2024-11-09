package com.miTurno.backend.controlador;


import com.miTurno.backend.DTO.Cliente;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.request.UsuarioRequest;
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

import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteControlador {

    private ClienteService clienteService;

    @Autowired
    public ClienteControlador(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

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
    @Operation(summary = "Obtener un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "El cliente con el ID fue devuelto"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerUsuarioPorId(@Parameter(description = "ID del cliente",example = "1")
                                                       @PathVariable Long id){
        Cliente cliente= clienteService.buscarUsuario(id);
        return ResponseEntity.ok(cliente);//200
    }

}

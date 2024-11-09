package com.miTurno.backend.controlador;


import com.miTurno.backend.DTO.Cliente;
import com.miTurno.backend.entidad.ClienteEntidad;
import com.miTurno.backend.mapper.ClienteMapper;
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

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteControlador(ClienteService clienteService,ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
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
        ClienteEntidad clienteEntidad = clienteMapper.toEntidad(usuarioRequest);
        return clienteService.actualizarClientePorId(id,clienteMapper.toModel(clienteEntidad));
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

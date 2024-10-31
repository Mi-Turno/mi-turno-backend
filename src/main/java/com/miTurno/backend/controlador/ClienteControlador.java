package com.miTurno.backend.controlador;


import com.miTurno.backend.DTO.Cliente;
import com.miTurno.backend.request.UsuarioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/negocios/{idNegocio}/clientes")
public class ClienteControlador {

//    private ClienteService clienteService;
//
//    @Autowired
//    public ClienteControlador(ClienteService clienteService) {
//        this.clienteService = clienteService;
//    }
//
//    // POST cliente por negocio (el cliente ya debe ser un usuario)
//    @PostMapping
//    @Operation(summary = "Agregar un cliente a un negocio")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Cliente agregado correctamente"),
//            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
//            @ApiResponse(responseCode = "404", description = "Negocio no encontrado")
//    })
//    public ResponseEntity<Cliente> agregarCliente(@PathVariable Long idNegocio, @RequestBody UsuarioRequest clienteRequest) {
//        Cliente nuevoCliente = clienteService.agregarCliente(idNegocio, clienteRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
//    }
//
//    // GET todos los clientes de un negocio
//    @GetMapping
//    @Operation(summary = "Obtener todos los clientes de un negocio")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente"),
//            @ApiResponse(responseCode = "404", description = "Negocio no encontrado")
//    })
//    public ResponseEntity<List<ClienteDTO>> obtenerClientesPorNegocio(@PathVariable Long idNegocio) {
//        List<ClienteDTO> clientes = clienteService.obtenerClientesPorNegocio(idNegocio);
//        return ResponseEntity.ok(clientes);
//    }
//
//    // GET un cliente de un negocio por id
//    @GetMapping("/{idCliente}")
//    @Operation(summary = "Obtener un cliente de un negocio por ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
//            @ApiResponse(responseCode = "404", description = "Cliente o negocio no encontrado")
//    })
//    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable Long idNegocio, @PathVariable Long idCliente) {
//        ClienteDTO cliente = clienteService.obtenerClientePorId(idNegocio, idCliente);
//        return ResponseEntity.ok(cliente);
//    }


}

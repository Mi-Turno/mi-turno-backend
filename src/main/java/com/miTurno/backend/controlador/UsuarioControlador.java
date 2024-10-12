package com.miTurno.backend.controlador;


import com.miTurno.backend.DTO.UsuarioRequest;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.modelo.Usuario;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioControlador(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    //GET
    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de usuarios obtenida"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping
    public List<UsuarioEntidad> listarUsuarios(){
        return usuarioService.obtenerTodosLosUsuarios();
    }
    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "El usuario con el ID fue devuelto"),
            @ApiResponse(responseCode = "400",description = "Parametros invalidos")
    })
    @GetMapping("/{id}")//todo hacer exception para no usar Optional
    public UsuarioEntidad listarUsuarios(@Parameter(description = "ID del usuario",example = "1")
                                                       @PathVariable Long id){
        return usuarioService.buscarUsuario(id);
    }
   //POST
    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos del usuario inválidos", content = @Content(schema =
            @Schema(implementation = Map.Entry.class), examples = @ExampleObject(value = "{ \"nombre\": \"no puede estar vacío\" }")))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crearUnUsuario(@Parameter(description = "Datos del usuario")
                                  @Valid @RequestBody UsuarioRequest usuarioRequest) {
        Usuario nuevoUsuario = new Usuario(
                usuarioRequest.getIdUsuario(),
                usuarioRequest.getNombre(),
                usuarioRequest.getApellido(),
                usuarioRequest.getCorreoElectronico(),
                usuarioRequest.getCelular(),
                usuarioRequest.getFechaNacimiento(),
                usuarioRequest.getRolUsuarioEnum());

        return usuarioService.crearUnUsuario(nuevoUsuario);
    }
    //UPDATE
    @Operation(summary = "actualizar usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuario actualizado con exito"),
            @ApiResponse(responseCode = "404",description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(
            @Parameter(description = "ID del usuario para actualizar",example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizado del usuario")
            @RequestBody UsuarioRequest usuarioRequest){
        Usuario actualizado = new Usuario(
                usuarioRequest.getIdUsuario(),
                usuarioRequest.getNombre(),
                usuarioRequest.getApellido(),
                usuarioRequest.getCorreoElectronico(),
                usuarioRequest.getCelular(),
                usuarioRequest.getFechaNacimiento(),
                usuarioRequest.getRolUsuarioEnum());
        return usuarioService.actualizarUsuarioPorId(id,actualizado);
    }

   //DELETE
    @Operation(summary = "Eliminar un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "El usuario fue borrado con exito"),
            @ApiResponse(responseCode = "404",description = "El usuario no fue encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUnUsuarioPorId(Long id){
         boolean respuesta = usuarioService.eliminarUsuarioPorId(id);
         if(respuesta){
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
         }else{
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
         }
    }






}
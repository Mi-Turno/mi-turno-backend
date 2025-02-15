package com.miTurno.backend.controlador;

import com.miTurno.backend.servicio.ArchivosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/archivos")
public class ArchivosControlador {
    private final ArchivosService archivosService;

    public ArchivosControlador(ArchivosService archivosService) {
        this.archivosService = archivosService;
    }

    //post un archivo

    @PostMapping(value = "/subir", consumes = "multipart/form-data")
    @Operation(
            summary = "Subir un archivo asociado a un ID",
            description = "Permite subir un archivo y guardarlo con respecto al ID del usuario.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Archivo subido correctamente."),
                    @ApiResponse(responseCode = "400", description = "Error en la solicitud."),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error al guardar el archivo.")
            }
    )
    public ResponseEntity<Boolean> subirArchivo(
            @Parameter(description = "ID del usuario", required = true, example = "123")
            @RequestParam Long id,
            @Parameter(description = "Archivo a enviar", required = true)
            @RequestParam MultipartFile archivo
            ) throws IOException {

        Boolean sePudoGuardar= archivosService.guardarFotoPerfilUsuario(id, archivo);

        return ResponseEntity.ok(sePudoGuardar);
    }

    @DeleteMapping(value = "/eliminar")
    @Operation(
            summary = "Eliminar un archivo asociado a un ID",
            description = "Permite eliminar un archivo con respecto al ID del usuario.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Archivo eliminado correctamente."),
                    @ApiResponse(responseCode = "400", description = "Error en la solicitud."),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error servidor.")
            }
    )
    public ResponseEntity<Boolean> eliminarArchivo(
            @Parameter(description = "ID del usuario", required = true, example = "123")
            @RequestParam Long id
    ) throws IOException {

        Boolean sePudoEliminar= archivosService.eliminarFotoPerfilUsuario(id);

        return ResponseEntity.ok(sePudoEliminar);
    }



}

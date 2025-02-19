package com.miTurno.backend.controlador;

import com.miTurno.backend.servicio.ArchivosService;
import com.miTurno.backend.tipos.EntidadEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/archivos")
@CrossOrigin("*") // Permitir acceso desde Angular
public class ArchivosControlador {
    private final ArchivosService archivosService;

    public ArchivosControlador(ArchivosService archivosService) {
        this.archivosService = archivosService;
    }

    //post un archivo

    @PostMapping(value = "/subir", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
            @Parameter(description = "ID de la entidad", required = true, example = "123")
            @RequestParam("id") Long id,
            @Parameter(description = "Archivo a enviar", required = true)
            @RequestParam("archivo") MultipartFile archivo,
            @Parameter(description = "Entidad", required = true, example = "USUARIO")
            @RequestParam("entidad") String nombreEntidadStr
            ) throws IOException {

        // Convertimos el String a Enum manualmente
        EntidadEnum nombreEntidad;
        try {
            nombreEntidad = EntidadEnum.valueOf(nombreEntidadStr.toUpperCase()); // Convierte el String a Enum
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false); // Manejo de error si el valor no es válido
        }

        Boolean sePudoGuardar= archivosService.guardarFotoPerfilUsuario(id, archivo,nombreEntidad);

        return ResponseEntity.ok(sePudoGuardar);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un archivo asociado a un ID.",
            description = "Permite obtener un archivo a partir del nombre relacionado a un usuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Archivo obtenido correctamente."),
                    @ApiResponse(responseCode = "400", description = "Error en la solicitud."),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error servidor.")
            }
    )
    public ResponseEntity<Resource> getArchivo(
            @Parameter(description = "ID del usuario", required = true, example = "123")
            @PathVariable Long id
    ) throws IOException {

        Resource resource = archivosService.obtenerFotoPerfil(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "nombreArchivo" + "\"")
                .body(resource);
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

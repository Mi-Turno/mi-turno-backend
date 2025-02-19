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

    @PostMapping(value = "/subirArchivoUsuario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<Boolean> subirArchivoUsuario(
            @Parameter(description = "ID de la entidad", required = true, example = "123")
            @RequestParam("idUsuario") Long id,
            @Parameter(description = "Archivo a enviar", required = true)
            @RequestParam("archivo") MultipartFile archivo
            ) throws IOException {

        Boolean sePudoGuardar= archivosService.guardarFotoPerfilUsuario(id, archivo);

        return ResponseEntity.ok(sePudoGuardar);
    }

    @PostMapping(value = "/subirArchivoServicio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<Boolean> subirArchivoServicio(
            @Parameter(description = "ID de la entidad", required = true, example = "4")
            @RequestParam("idServicio") Long idServicio,
            @Parameter(description = "ID de la entidad", required = true, example = "2")
            @RequestParam("idNegocio") Long idNegocio,
            @Parameter(description = "Archivo a enviar", required = true)
            @RequestParam("archivo") MultipartFile archivo
    ) throws IOException {

        Boolean sePudoGuardar= archivosService.guardarFotoServicio(idServicio,idNegocio, archivo);

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
    public ResponseEntity<Resource> getArchivoUsuario(
            @Parameter(description = "ID del usuario", required = true, example = "123")
            @PathVariable Long id
    ) throws IOException {

        Resource resource = archivosService.obtenerFotoPerfilUsuario(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "nombreArchivo" + "\"")
                .body(resource);
    }

    @GetMapping("/{idNegocio}/{idServicio}")
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
    public ResponseEntity<Resource> getArchivoServicio(
            @Parameter(description = "ID del negocio", required = true, example = "2")
            @PathVariable Long idNegocio,
            @Parameter(description = "ID del servicio", required = true, example = "1")
            @PathVariable Long idServicio
    ) throws IOException {

        Resource resource = archivosService.obtenerFotoServicio(idServicio,idNegocio);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "nombreArchivo" + "\"")
                .body(resource);
    }



    @DeleteMapping(value = "/eliminarArchivoUsuario")
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
    public ResponseEntity<Boolean> eliminarArchivoUsuario(
            @Parameter(description = "ID del usuario", required = true, example = "123")
            @RequestParam Long id
    ) throws IOException {

        Boolean sePudoEliminar= archivosService.eliminarFotoPerfilUsuario(id);

        return ResponseEntity.ok(sePudoEliminar);
    }

    @DeleteMapping(value = "/eliminarArchivoServicio")
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
    public ResponseEntity<Boolean> eliminarArchivoServicio(
            @Parameter(description = "ID del servicio", required = true, example = "123")
            @RequestParam Long id
    ) throws IOException {

        Boolean sePudoEliminar= archivosService.eliminarFotoServicio(id);

        return ResponseEntity.ok(sePudoEliminar);
    }



}

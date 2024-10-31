package com.miTurno.backend.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@Builder
@Schema(description = "Requisitos para la creacion de un usuario ")
public class UsuarioRequest {

//    @Schema(description = "Identificador único del usuario", example = "1")
//    private Long idUsuario;

    @Schema(description = "El nombre del usuario", example = "Juan")
    //@Size(min = 3, max = 50)
    private String nombre;

    @Schema(description = "El apellido del usuario", example = "Perez")
    //@Size(min = 3, max = 50)
    private String apellido;

    @Schema(description = "El Email del usuario", example = "Juan@example.com")
    @Email
    private String email;

    //@Size(min = 6, max = 10)
    @Schema(description = "La contraseña del usuario", example = "example1")
    private String password;

    @Schema(description = "El telefono del usuario", example = "12345678")
    private String telefono;

    @Schema(description = "La fecha del usuario", example = "2004-10-10")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)

    @Schema(description = "ID del rol del usuario", example = "2")//"CLIENTE", "ADMIN", "PROFESIONAL", "NEGOCIO"
    private Long idRol;



    public UsuarioRequest() {

    }
}
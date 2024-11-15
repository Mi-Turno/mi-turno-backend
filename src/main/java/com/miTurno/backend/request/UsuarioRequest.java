package com.miTurno.backend.request;


import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@Schema(description = "Requisitos para la creacion de un usuario ")
public class UsuarioRequest {


    @Schema(description = "El nombre del usuario", example = "Juan")
    //@Size(min = 3, max = 50)
    private String nombre;

    @Schema(description = "El apellido del usuario", example = "Perez")
    //@Size(min = 3, max = 50)
    private String apellido;

    @Schema(description = "La fecha del usuario", example = "2004-10-10")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)

    @Schema(description = "Credenciales necesarias para la creacion del usuario")
    private CredencialRequest crendeciales;

    @Schema(description = "Rol del usuario", example = "CLIENTE")//"CLIENTE", "ADMIN", "PROFESIONAL", "NEGOCIO"
    private RolUsuarioEnum rolUsuarioEnum;


    public UsuarioRequest() {
    super();
    }

    public UsuarioRequest(String apellido, CredencialRequest crendeciales, LocalDate fechaNacimiento, String nombre) {
        this.apellido = apellido;
        this.crendeciales = crendeciales;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
    }
}

package com.miTurno.backend.DTO;

import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "Requisitos para la creacion de un profesional")
@Builder
@Getter
@Setter
public class ProfesionalRequest {

    @Schema(description = "id del negocio al que esta relacionado", example = "4")
    private Long idNegocio;

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

    @Schema(description = "El rol del usuario", example = "CLIENTE", allowableValues = {"CLIENTE", "ADMIN", "PROFESIONAL", "NEGOCIO"})
    private RolUsuarioEnum rolEntidad;

    public ProfesionalRequest(Long idNegocio,String nombre, String apellido, String email, String password, String telefono, LocalDate fechaNacimiento, RolUsuarioEnum rolEntidad) {
        this.idNegocio = idNegocio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.rolEntidad = rolEntidad;
    }

    public ProfesionalRequest() {

    }
}

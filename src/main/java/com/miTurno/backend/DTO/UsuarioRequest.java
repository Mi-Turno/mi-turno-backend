package com.miTurno.backend.DTO;


import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "Requisitos para la creacion de un usuario ")
public class UsuarioRequest {

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long idUsuario;
    @Schema(description = "El nombre del usuario", example = "Juan")
    @Size(min = 3, max = 50)
    private String nombre;
    @Schema(description = "El apellido del usuario", example = "Perez")
    @Size(min = 3, max = 50)
    private String apellido;
    @Schema(description = "El correo del usuario", example = "Juan@example.com")
    @Email
    private String correoElectronico;
    @Schema(description = "El celular del usuario", example = "12345678")
    private String celular;

    @Schema(description = "La fecha del usuario", example = "2004-10-10")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)
    @Schema(description = "El rol del usuario", example = "ADMIN")
    private RolUsuarioEnum rolUsuarioEnum;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getCelular() {
        return celular;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public RolUsuarioEnum getRolUsuarioEnum() {
        return rolUsuarioEnum;
    }
}

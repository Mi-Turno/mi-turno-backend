package com.miTurno.backend.DTO;


import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Builder
@Schema(description = "Requisitos para la creacion de un usuario ")
public class UsuarioRequest {

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long idUsuario;

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
    @Schema(description = "El rol del usuario", example = "CLIENTE")
    private RolUsuarioEnum rol;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public RolUsuarioEnum getRol() {
        return rol;
    }

    public UsuarioRequest() {
    }

    public UsuarioRequest(Long idUsuario, String nombre, String apellido, String correoElectronico, String celular, LocalDate fechaNacimiento, RolUsuarioEnum rolUsuarioEnum) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.rolUsuarioEnum = rolUsuarioEnum;
    }
}

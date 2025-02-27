package com.miTurno.backend.data.dtos.request;


import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@Schema(description = "Requisitos para la creacion de un usuario ")
@AllArgsConstructor
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
    private CredencialRequest credencial;

    @Schema(description = "Rol del usuario", example = "CLIENTE")//"CLIENTE", "ADMIN", "PROFESIONAL", "NEGOCIO"
    private RolUsuarioEnum rolUsuario;

    public UsuarioRequest() {
    super();
    }

}

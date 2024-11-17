package com.miTurno.backend.request;

import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Requisitos para la creacion de crendeciales de un usuario ")
public class CredencialRequest {

    @Email
    @Schema(description = "El email del usuario", example = "Juan123@gmail.com")
    private String email;

    @Schema(description = "La contraseña del usuario", example = "asd23545")
    private String password;

    @Schema(description = "El nro de telefono del usuario", example = "22335667889")
    private String telefono;

//    @Schema(description = "El rol del usuario", example = "CLIENTE")
//    private RolUsuarioEnum rolUsuarioEnum;


    public CredencialRequest() {
    }
}

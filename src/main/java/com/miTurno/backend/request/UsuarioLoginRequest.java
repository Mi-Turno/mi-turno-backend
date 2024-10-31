package com.miTurno.backend.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioLoginRequest {
    @Schema(description = "Email del usuario",example = "example@gmail.com")
    @Email
    private String email;

    //@Size(min = 6, max = 10)
    @Schema(description = "La contraseña del usuario", example = "example1")
    private String password;

    public UsuarioLoginRequest(){

    }
    public UsuarioLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

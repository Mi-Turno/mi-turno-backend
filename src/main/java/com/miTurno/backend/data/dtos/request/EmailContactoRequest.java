package com.miTurno.backend.data.dtos.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Requisitos para enviar un mail de contacto de un cliente a Mi turno")
public class EmailContactoRequest {

    @Schema(description = "Nombre del cliente",example = "Juan")
    private String nombre;

    @Schema(description = "Nombre del negocio del cliente",example = "Barber")
    private String negocio;

    @Schema(description = "Correo electronico del cliente",example = "cliente@gmail.com")
    @Email
    private String email;


    @Schema(description = "Mensaje del cliente",example = "Este es un mensaje")
    private String mensaje;


    public EmailContactoRequest(String nombre, String negocio, String email, String mensaje) {
        this.nombre = nombre;
        this.negocio = negocio;
        this.email = email;
        this.mensaje = mensaje;
    }
}

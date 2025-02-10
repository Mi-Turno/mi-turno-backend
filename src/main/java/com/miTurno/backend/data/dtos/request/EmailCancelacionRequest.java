package com.miTurno.backend.data.dtos.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Requisitos para enviar un email de cancelacion")
public class EmailCancelacionRequest {

    @Schema(description = "Nombre del cliente",example = "Juan")
    private String nombreCliente;

    @Schema(description = "Nombre del negocio" , example = "Carlos Barber Shop")
    private String nombreNegocio;

    @Schema(description = "Correo electronico del cliente",example = "cliente@gmail.com")
    @Email
    private String emailCliente;

    @Schema(description = "Numero de telefono del local",example = "2234931245")
    private Long numeroSoporte;
    //todo quiza en un futuro haya que agregar el email del negocio
    public EmailCancelacionRequest(String nombreCliente, String nombreNegocio, String emailCliente, Long numeroSoporte) {
        this.nombreCliente = nombreCliente;
        this.nombreNegocio = nombreNegocio;
        this.emailCliente = emailCliente;
        this.numeroSoporte = numeroSoporte;
    }

    public EmailCancelacionRequest() {
    }
}

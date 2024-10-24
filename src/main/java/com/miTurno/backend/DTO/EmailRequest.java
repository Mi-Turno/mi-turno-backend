package com.miTurno.backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "Requisitos para la creacion de un usuario ")
public class EmailRequest {
    @Schema(description = "Correo de confirmacion",example = "Su turno se reservo correctamente")
    private String mensaje;
    @Schema(description = "Correo electronico del usuario",example = "example@gmail.com")
    @Email
    private String email;
    @Schema(description = "Correo electronico del negocio",example = "negocio@gmail.com")
    @Email
    private String emailNegocio;
    @Schema(description = "Servicio del negocio",example = "corte de pelo")
    private String servicio;
    @Schema(description = "Precio del servicio",example ="$10000")
    private Double precio;
    @Schema(description = "Direccion del negocio",example = "example 0000")
    private String direccion;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailNegocio() {
        return emailNegocio;
    }

    public void setEmailNegocio(String emailNegocio) {
        this.emailNegocio = emailNegocio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}

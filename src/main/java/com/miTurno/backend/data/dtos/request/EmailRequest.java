package com.miTurno.backend.data.dtos.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@Schema(description = "Requisitos para enviar un email desde un negocio a un cliente")
public class EmailRequest {

    @Schema(description = "Correo electronico del negocio",example = "negocio@gmail.com")
    @Email
    private String emailNegocio;

    @Schema(description = "Correo electronico del cliente",example = "cliente@gmail.com")
    @Email
    private String email;

    @Schema(description = "Correo de confirmacion",example = "Su turno se reservo correctamente")
    private String mensaje;

    @Schema(description = "Fecha del turno", example = "2024-11-24")
    private LocalDate fecha;

    @Schema(description = "Horario del turno",example = "13:30")
   // @JsonFormat(pattern = "HH:mm")
    private String horario;

    @Schema(description = "Ubicacion del negocio",example = "Mar del Plata, Buenos Aires, Argentina")
    private String ubicacion;

    @Schema(description = "Direccion del negocio",example = "Av.Pescadores 668")
    private String direccion;

    @Schema(description = "Servicio del negocio",example = "corte de pelo")
    private String servicio;

    @Schema(description = "Nombre del profesional",example = "Carlos")
    private String nombreProfesional;

    @Schema(description = "Precio del servicio",example ="$10000")
    private Double precio;

    public EmailRequest(String emailNegocio, String email, String mensaje, LocalDate fecha, String horario, String ubicacion, String direccion, String servicio, String nombreProfesional, Double precio) {
        this.emailNegocio = emailNegocio;
        this.email = email;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.horario = horario;
        this.ubicacion = ubicacion;
        this.direccion = direccion;
        this.servicio = servicio;
        this.nombreProfesional = nombreProfesional;
        this.precio = precio;
    }

}

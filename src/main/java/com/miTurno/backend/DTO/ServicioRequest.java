package com.miTurno.backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description="Requisitos para la creacion de un servicio")
@Builder
public class ServicioRequest {

    @Schema(description = "Nombre del servicio que se ofrece", example = "Corte con maquina", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "Duracion del servicio", example = "90",requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(1)
    @Max(600)
    private Integer duracion;

    @Schema(description = "Precio del servicio", example = "9000",requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(1)
    private Double precio;


    //constructores
    public ServicioRequest(){}

    public ServicioRequest(String nombre, Integer duracion, Double precio) {
        this.duracion = duracion;
        this.nombre = nombre;
        this.precio = precio;
    }
}

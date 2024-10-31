package com.miTurno.backend.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description="Requisitos para la creacion de un servicio")
@Builder
public class ServicioRequest {

    @Schema(description = "ID del negocio al que pertenece",example = "1")
    private Long idNegocio;

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

    public ServicioRequest(Long idNegocio, String nombre, Integer duracion, Double precio) {
        this.idNegocio = idNegocio;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
    }
}

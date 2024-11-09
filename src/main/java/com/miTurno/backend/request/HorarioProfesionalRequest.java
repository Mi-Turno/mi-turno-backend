package com.miTurno.backend.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.miTurno.backend.tipos.DiasEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@Schema(description="Requisitos para la creacion de un horario por profesional")
public class HorarioProfesionalRequest {

    @Schema(description = "ID del profesional al que pertenece el horario", example = "1")
    @NotNull
    private Long idProfesional;

    @Schema(description = "Día de la semana en que el profesional está disponible", example = "LUNES")
    @NotNull
    private DiasEnum dia;

    @Schema(type = "string", example = "10:30", description = "Formato de hora en HH:mm")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;




    public HorarioProfesionalRequest() {
    }

    public HorarioProfesionalRequest(Long idProfesional, DiasEnum dia, LocalTime horaInicio) {
        this.idProfesional = idProfesional;
        this.dia = dia;
        this.horaInicio = horaInicio;
    }
}

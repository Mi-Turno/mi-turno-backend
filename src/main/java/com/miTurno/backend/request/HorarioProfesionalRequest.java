package com.miTurno.backend.request;


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

    @Schema(description = "Hora de inicio del horario", example = "09:00")
    @NotNull
    private LocalTime horaInicio;

    @Schema(description = "Hora de fin del horario", example = "17:00")
    @NotNull
    private LocalTime horaFin;


    public HorarioProfesionalRequest() {
    }

    public HorarioProfesionalRequest(Long idProfesional, DiasEnum dia, LocalTime horaInicio, LocalTime horaFin) {
        this.idProfesional = idProfesional;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}

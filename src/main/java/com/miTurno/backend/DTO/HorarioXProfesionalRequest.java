package com.miTurno.backend.DTO;


import com.miTurno.backend.tipos.DiasEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@Schema(description="Requisitos para la creacion de un horario por profesional")
public class HorarioXProfesionalRequest {

    @Schema(description = "ID del profesional",example = "1")
    private Long idProfesional;
    @Schema(description = "Dia del horario",example = "LUNES")
    private DiasEnum dia;
    @Schema(description = "Horario del turno",example = "13:30")
    private LocalTime horario;

    public HorarioXProfesionalRequest() {
    }
    public HorarioXProfesionalRequest(Long idProfesional, DiasEnum dia, LocalTime horario) {
        this.idProfesional = idProfesional;
        this.dia = dia;
        this.horario = horario;
    }
}

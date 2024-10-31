package com.miTurno.backend.DTO;

import com.miTurno.backend.tipos.DiasEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@Builder
public class HorarioProfesional {
    private Long idHorario;
    private Long idProfesional;
    private DiasEnum dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public HorarioProfesional() {}
}
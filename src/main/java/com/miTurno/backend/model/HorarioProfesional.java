package com.miTurno.backend.model;

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


    public HorarioProfesional() {}

    public HorarioProfesional(Long idHorario, Long idProfesional, DiasEnum dia, LocalTime horaInicio) {
        this.idHorario = idHorario;
        this.idProfesional = idProfesional;
        this.dia = dia;
        this.horaInicio = horaInicio;

    }

    @Override
    public String toString() {
        return "HorarioProfesional{" +
                "dia=" + dia +
                ", horaInicio=" + horaInicio +
                ", idHorario=" + idHorario +
                ", idProfesional=" + idProfesional +
                '}';
    }
}

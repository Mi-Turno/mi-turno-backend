package com.miTurno.backend.data.dtos.model;

import com.miTurno.backend.tipos.DiasEnum;
import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@Builder

public class HorarioProfesional {
    private Long idHorario;
    private Long idProfesional;
    private DiasEnum dia;
    private LocalTime horaInicio;
    private Boolean estado;


    public HorarioProfesional() {}

    public HorarioProfesional(Long idHorario, Long idProfesional, DiasEnum dia, LocalTime horaInicio, Boolean estado) {
        this.idHorario = idHorario;
        this.idProfesional = idProfesional;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "HorarioProfesional{" +
                "dia=" + dia +
                ", estado=" + estado +
                ", horaInicio=" + horaInicio +
                ", idHorario=" + idHorario +
                ", idProfesional=" + idProfesional +
                '}';
    }
}

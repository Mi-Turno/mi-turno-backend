package com.miTurno.backend.modelo;


import com.miTurno.backend.tipos.DiasEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class HorarioXProfesional {
    private Long idHorarioXProfesional;
    private Long idProfesional;
    private DiasEnum dia;
    private LocalTime horario;

    public HorarioXProfesional(Long idHorarioXProfesional, Long idProfesional, DiasEnum dia, LocalTime horario) {
        this.idHorarioXProfesional = idHorarioXProfesional;
        this.idProfesional = idProfesional;
        this.dia = dia;
        this.horario = horario;
    }
    public HorarioXProfesional(){

    }

    @Override
    public String toString() {
        return "HorarioXProfesional{" +
                "idHorarioXProfesional=" + idHorarioXProfesional +
                ", idProfesional=" + idProfesional +
                ", dia=" + dia +
                ", horario=" + horario +
                '}';
    }
}

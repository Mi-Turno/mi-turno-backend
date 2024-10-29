package com.miTurno.backend.mapper;


import com.miTurno.backend.DTO.HorarioXProfesionalRequest;
import com.miTurno.backend.entidad.DiaEntidad;
import com.miTurno.backend.entidad.HorarioXProfesionalEntidad;
import com.miTurno.backend.modelo.HorarioXProfesional;
import org.springframework.stereotype.Component;

@Component
public class HorarioXProfesionalMapper {

    //entidad a HorarioXProfesional
    public HorarioXProfesional toModel(HorarioXProfesionalEntidad horarioXProfesionalEntity){

        return HorarioXProfesional.builder()
                .idHorarioXProfesional(horarioXProfesionalEntity.getIdHorarioXProfesional())
                .idProfesional(horarioXProfesionalEntity.getIdProfesional())
                .dia(horarioXProfesionalEntity.getDiaEntidad().getDia())
                .horario(horarioXProfesionalEntity.getHorario())
                .build();
    }
    //request a HorarioXProfesional
    public HorarioXProfesional toModel(HorarioXProfesionalRequest horarioXProfesionalRequest){
        return HorarioXProfesional.builder()
                .idProfesional(horarioXProfesionalRequest.getIdProfesional())
                .dia(horarioXProfesionalRequest.getDia())
                .horario(horarioXProfesionalRequest.getHorario())
                .build();
    }

}

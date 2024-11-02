package com.miTurno.backend.mapper;


import com.miTurno.backend.DTO.HorarioProfesional;
import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.request.HorarioProfesionalRequest;

import org.springframework.stereotype.Component;

@Component
public class HorarioProfesionalMapper {

    //entidad a HorarioProfesional
    public HorarioProfesional toModel(HorarioProfesionalEntidad horarioProfesionalEntidad){



        return HorarioProfesional.builder()
                .idHorario(horarioProfesionalEntidad.getIdHorario())
                .idProfesional(horarioProfesionalEntidad.getProfesionalEntidad().getIdUsuario())
                .dia(horarioProfesionalEntidad.getDiaEntidad().getDia())
                .horaInicio(horarioProfesionalEntidad.getHoraInicio())
//                .horaFin(horarioProfesionalEntidad.getHoraFin())
                .build();
    }

    //request a HorarioXProfesional
    public HorarioProfesional toModel(HorarioProfesionalRequest horarioProfesionalRequest){

        return HorarioProfesional.builder()
//                .idProfesional(horarioProfesionalRequest.getIdProfesional())
                .dia(horarioProfesionalRequest.getDia())
                .horaInicio(horarioProfesionalRequest.getHoraInicio())
//                .horaFin(horarioProfesionalRequest.getHoraFin())
                .build();
    }

}

package com.miTurno.backend.mapper;


import com.miTurno.backend.model.HorarioProfesional;
import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.request.HorarioProfesionalRequest;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class HorarioProfesionalMapper {

    public HorarioProfesionalMapper() {
    }

    //entidad a HorarioProfesional
    public HorarioProfesional toModel(HorarioProfesionalEntidad horarioProfesionalEntidad){



        return HorarioProfesional.builder()
                .idHorario(horarioProfesionalEntidad.getId())
                .idProfesional(horarioProfesionalEntidad.getProfesionalEntidad().getId())
                .dia(horarioProfesionalEntidad.getDiaEntidad().getDia())
                .horaInicio(horarioProfesionalEntidad.getHoraInicio())
//                .horaFin(horarioProfesionalEntidad.getHoraFin())
                .build();
    }
    //lista de entidad a lista horario
    public List<HorarioProfesional> toModelList(List<HorarioProfesionalEntidad> listaHorarioProfesionalEntidad) {
        // Si la lista es null, retorna una lista vacía en lugar de null
        if (listaHorarioProfesionalEntidad == null) {
            return Collections.emptyList();
        }

        return listaHorarioProfesionalEntidad.stream()
                .map(this::toModel)
                .toList();
    }

    //request a HorarioXProfesional
    public HorarioProfesional toModel(HorarioProfesionalRequest horarioProfesionalRequest, Long idProfesional){

        return HorarioProfesional.builder()
                .idProfesional(idProfesional)
                .dia(horarioProfesionalRequest.getDia())
                .horaInicio(horarioProfesionalRequest.getHoraInicio())
//                .horaFin(horarioProfesionalRequest.getHoraFin())
                .build();
    }

}

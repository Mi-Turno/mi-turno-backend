package com.miTurno.backend.data.mapper;


import com.miTurno.backend.data.dtos.response.HorarioProfesional;
import com.miTurno.backend.data.domain.HorarioProfesionalEntidad;
import com.miTurno.backend.data.dtos.request.HorarioProfesionalRequest;
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
                .estado(horarioProfesionalEntidad.getEstado())
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
                .estado(true)
                .build();
    }

}

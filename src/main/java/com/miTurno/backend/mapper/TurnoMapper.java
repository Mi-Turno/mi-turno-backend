package com.miTurno.backend.mapper;

import com.miTurno.backend.entidad.EstadoTurnoEntidad;
import com.miTurno.backend.request.TurnoRequest;
import com.miTurno.backend.entidad.TurnoEntidad;
import com.miTurno.backend.model.Turno;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TurnoMapper {


    private final HorarioProfesionalMapper horarioProfesionalMapper;

    public TurnoMapper(HorarioProfesionalMapper horarioProfesionalMapper) {
        this.horarioProfesionalMapper = horarioProfesionalMapper;
    }

    //entidad a turno
    public Turno toModel(TurnoEntidad turnoEntidad){
        EstadoTurnoEntidad estadoTurnoEntidad = turnoEntidad.getEstadoTurno();
        return Turno.builder()
                .idTurno(turnoEntidad.getId())
                .idServicio(turnoEntidad.getIdServicio().getId())
                .metodosDePagoEnum(turnoEntidad.getMetodoDePagoEntidad().getMetodoDePago())
                .idCliente(turnoEntidad.getClienteEntidad().getId())
                .idNegocio(turnoEntidad.getNegocioEntidad().getId())
                .horarioProfesional(horarioProfesionalMapper.toModel(turnoEntidad.getHorarioProfesionalEntidad()))
                .idProfesional(turnoEntidad.getProfesionalEntidad().getId())
                .estado(estadoTurnoEntidad.getEstadoTurno())
                .fechaInicio(turnoEntidad.getFechaInicio())
                .build();

    }
    public List<Turno> toModelList(List<TurnoEntidad> listaTurnoEntidad) {
        // Si la lista es null, retorna una lista vacía en lugar de null
        if (listaTurnoEntidad == null) {
            return Collections.emptyList();
        }

        return listaTurnoEntidad.stream()
                .map(this::toModel)
                .toList();
    }


    //request a turno
    public Turno toModel(TurnoRequest turnoRequest,Long idNegocio){


        return Turno.builder()
                .horarioProfesional(turnoRequest.getHorarioProfesional())
                .idProfesional(turnoRequest.getIdProfesional())
                .idServicio(turnoRequest.getIdServicio())
                .metodosDePagoEnum(turnoRequest.getMetodosDePagoEnum())
                .idCliente(turnoRequest.getIdCliente())
                .idNegocio(idNegocio)
                .fechaInicio(turnoRequest.getFechaInicio())
                .build();
    }

}

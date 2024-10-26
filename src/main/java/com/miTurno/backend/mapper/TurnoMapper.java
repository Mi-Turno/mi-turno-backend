package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.TurnoRequest;
import com.miTurno.backend.entidad.TurnoEntidad;
import com.miTurno.backend.modelo.Turno;
import org.springframework.stereotype.Component;

@Component
public class TurnoMapper {

    //entidad a turno
    public Turno toModel(TurnoEntidad turnoEntidad){
        return Turno.builder()
                .idTurno(turnoEntidad.getIdTurno())
                .idProfesional(turnoEntidad.getIdProfesional())
                .idCliente(turnoEntidad.getIdCliente())
                .fechaInicio(turnoEntidad.getFechaInicio())
                .horario(turnoEntidad.getHorario())
                .estado(turnoEntidad.getEstado())
                .build();
    }

    public Turno toModel(TurnoRequest turnoRequest){
        return Turno.builder()
                .idProfesional(turnoRequest.getIdProfesional())
                .idCliente(turnoRequest.getIdCliente())
                .idNegocio(turnoRequest.getIdNegocio())
                .fechaInicio(turnoRequest.getFechaInicio())
                .horario(turnoRequest.getHorario())
                .estado(true)
                .build();
    }

}

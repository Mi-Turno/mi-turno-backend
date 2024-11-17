package com.miTurno.backend.mapper;

import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.repositorio.HorarioProfesionalRepositorio;
import com.miTurno.backend.request.TurnoRequest;
import com.miTurno.backend.entidad.TurnoEntidad;
import com.miTurno.backend.DTO.Turno;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TurnoMapper {



    public TurnoMapper() {

    }

    //entidad a turno
    public Turno toModel(TurnoEntidad turnoEntidad){

        return Turno.builder()
                .idTurno(turnoEntidad.getId())
                .idServicio(turnoEntidad.getIdServicio().getId())
                .metodosDePagoEnum(turnoEntidad.getMetodoDePagoEntidad().getMetodoDePago())
                .idCliente(turnoEntidad.getClienteEntidad().getId())
                .idNegocio(turnoEntidad.getNegocioEntidad().getId())
                .idHorarioProfesional(turnoEntidad.getHorarioProfesionalEntidad().getId())
                .idProfesional(turnoEntidad.getProfesionalEntidad().getId())
                .estado(turnoEntidad.getEstado())
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
    public Turno toModel(TurnoRequest turnoRequest){


        return Turno.builder()
                .idHorarioProfesional(turnoRequest.getIdHorarioProfesional())
                .idProfesional(turnoRequest.getIdProfesional())
                .idServicio(turnoRequest.getIdServicio())
                .metodosDePagoEnum(turnoRequest.getMetodosDePagoEnum())
                .idCliente(turnoRequest.getIdCliente())
                .idNegocio(turnoRequest.getIdNegocio())
                .fechaInicio(turnoRequest.getFechaInicio())
                .build();
    }

}

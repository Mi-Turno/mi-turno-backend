package com.miTurno.backend.mapper;

import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.repositorio.HorarioProfesionalRepositorio;
import com.miTurno.backend.repositorio.MetodosDePagoRepositorio;
import com.miTurno.backend.request.TurnoRequest;
import com.miTurno.backend.entidad.TurnoEntidad;
import com.miTurno.backend.DTO.Turno;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TurnoMapper {

    private final MetodosDePagoRepositorio metodosDePagoRepositorio;
    private final HorarioProfesionalMapper horarioProfesionalMapper;
    private final HorarioProfesionalRepositorio horarioProfesionalRepositorio;

    public TurnoMapper(MetodosDePagoRepositorio metodosDePagoRepositorio, HorarioProfesionalMapper horarioProfesionalMapper, HorarioProfesionalRepositorio horarioProfesionalRepositorio) {
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
        this.horarioProfesionalMapper = horarioProfesionalMapper;
        this.horarioProfesionalRepositorio = horarioProfesionalRepositorio;
    }

    //entidad a turno
    public Turno toModel(TurnoEntidad turnoEntidad){

        MetodosDePagoEnum metodosDePagoEnum= metodosDePagoRepositorio.findById(turnoEntidad.getMetodoDePagoEntidad().getId()).get().getMetodosDePago();

        return Turno.builder()
                .idTurno(turnoEntidad.getId())
                .idServicio(turnoEntidad.getIdServicio().getId())
                .metodosDePagoEnum(metodosDePagoEnum)
                .idCliente(turnoEntidad.getClienteEntidad().getId())
                .idNegocio(turnoEntidad.getNegocioEntidad().getId())
                .horarioProfesional(horarioProfesionalMapper.toModel(turnoEntidad.getHorarioProfesionalEntidad()))
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


        System.out.println("ID HORARIO PROFESONAL"+turnoRequest.getIdHorarioProfesional());
        HorarioProfesionalEntidad horarioProfesionalEntidad= horarioProfesionalRepositorio.findById(turnoRequest.getIdHorarioProfesional()).orElseThrow(()-> new RecursoNoExisteException("id horario"));


        return Turno.builder()
                .horarioProfesional(horarioProfesionalMapper.toModel(horarioProfesionalEntidad))
                .idProfesional(turnoRequest.getIdProfesional())
                .idServicio(turnoRequest.getIdServicio())
                .metodosDePagoEnum(turnoRequest.getMetodosDePagoEnum())
                .idCliente(turnoRequest.getIdCliente())
                .idNegocio(turnoRequest.getIdNegocio())
                .fechaInicio(turnoRequest.getFechaInicio())
                .build();
    }

}

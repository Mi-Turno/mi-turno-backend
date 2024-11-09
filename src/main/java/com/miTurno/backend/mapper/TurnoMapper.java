package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.HorarioProfesional;
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

    public TurnoMapper(MetodosDePagoRepositorio metodosDePagoRepositorio, HorarioProfesionalMapper horarioProfesionalMapper) {
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
        this.horarioProfesionalMapper = horarioProfesionalMapper;
    }

    //entidad a turno
    public Turno toModel(TurnoEntidad turnoEntidad){

        MetodosDePagoEnum metodosDePagoEnum= metodosDePagoRepositorio.findById(turnoEntidad.getMetodoDePagoEntidad().getId_metodo_de_pago()).get().getMetodosDePago();

        return Turno.builder()
                .idServicio(turnoEntidad.getIdServicio().getIdServicio())
                .idTurno(turnoEntidad.getIdTurno())
                .metodosDePagoEnum(metodosDePagoEnum)
                .idCliente(turnoEntidad.getIdTurno())
                .idNegocio(turnoEntidad.getNegocioEntidad().getIdUsuario())
                .idHorarioProfesional(turnoEntidad.getHorarioProfesionalEntidad().getIdHorario())
                .idProfesional(turnoEntidad.getProfesionalEntidad().getIdUsuario())
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


        MetodosDePagoEnum metodosDePagoEnum= metodosDePagoRepositorio.findById(turnoRequest.getIdMetodoDePago()).get().getMetodosDePago();

        return Turno.builder()
                .idHorarioProfesional(turnoRequest.getIdHorarioProfesional())
                .idProfesional(turnoRequest.getIdProfesional())
                .idServicio(turnoRequest.getIdServicio())
                .metodosDePagoEnum(metodosDePagoEnum)
                .idCliente(turnoRequest.getIdCliente())
                .idNegocio(turnoRequest.getIdNegocio())
                .fechaInicio(turnoRequest.getFechaInicio())
                .build();
    }

}

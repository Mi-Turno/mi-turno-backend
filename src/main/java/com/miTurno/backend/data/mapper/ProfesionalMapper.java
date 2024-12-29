package com.miTurno.backend.data.mapper;

import com.miTurno.backend.data.dtos.response.Credencial;
import com.miTurno.backend.data.dtos.response.Profesional;
import com.miTurno.backend.data.domain.CredencialEntidad;
import com.miTurno.backend.data.domain.NegocioEntidad;
import com.miTurno.backend.data.domain.ProfesionalEntidad;
import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.data.dtos.request.ProfesionalRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProfesionalMapper {



    private final ServicioMapper servicioMapper;
    private final HorarioProfesionalMapper profesionalMapper;
    private final TurnoMapper turnoMapper;

    private final CredencialMapper credencialMapper;

    public ProfesionalMapper(ServicioMapper servicioMapper, HorarioProfesionalMapper profesionalMapper, TurnoMapper turnoMapper, CredencialMapper credencialMapper) {

        this.servicioMapper = servicioMapper;
        this.profesionalMapper = profesionalMapper;
        this.turnoMapper = turnoMapper;

        this.credencialMapper = credencialMapper;
    }

    //request a entidad
    public ProfesionalEntidad toEntidad(ProfesionalRequest profesionalRequest, NegocioEntidad negocioEntidad, RolEntidad rolEntidad){


    //todo esto se puede mejorar, haciendo un mapper de request a entidad
        CredencialEntidad credencialEntidad = credencialMapper.toEntidad(credencialMapper.toModel(profesionalRequest.getCredencial()));


        return ProfesionalEntidad.builder()
                .negocioEntidad(negocioEntidad)
                .nombre(profesionalRequest.getNombre())
                .apellido(profesionalRequest.getApellido())
                .fechaNacimiento(profesionalRequest.getFechaNacimiento())
                .credencial(credencialEntidad)
                .rolEntidad(rolEntidad)
                .build();

    }

    //entidad a modelo
    public Profesional toModel(ProfesionalEntidad profesionalEntidad){

        Credencial unaCredencial = credencialMapper.toModel(profesionalEntidad.getCredencial());

        return Profesional.builder()
                .idUsuario(profesionalEntidad.getId())
                .apellido(profesionalEntidad.getApellido())
                .nombre(profesionalEntidad.getNombre())
                .rolUsuario(profesionalEntidad.getRolEntidad().getRol())
                .fechaNacimiento(profesionalEntidad.getFechaNacimiento())
                .idNegocio(profesionalEntidad.getNegocioEntidad().getId())

                .credencial(unaCredencial)

                .listaServicios(servicioMapper.toModelList(profesionalEntidad.getListaServiciosEntidad()))
                .horariosDisponibles(profesionalMapper.toModelList(profesionalEntidad.getHorariosDisponibles()))
                .turnosAgendados(turnoMapper.toModelList(profesionalEntidad.getTurnosAgendados()))
                .build();
    }
    public List<Profesional> toModelList(List<ProfesionalEntidad> listaProfesionalEntidad) {
        // Si la lista es null, retorna una lista vacía en lugar de null
        if (listaProfesionalEntidad == null) {
            return Collections.emptyList();
        }

        return listaProfesionalEntidad.stream()
                .map(this::toModel)
                .toList();
    }




    //request a modelo

    public Profesional toModel(ProfesionalRequest profesionalRequest){

        Credencial unaCredencial= credencialMapper.toModel(profesionalRequest.getCredencial());


        return Profesional.builder()
                .credencial(unaCredencial)
                .apellido(profesionalRequest.getApellido())
                .nombre(profesionalRequest.getNombre())
                .fechaNacimiento(profesionalRequest.getFechaNacimiento())
//                .idNegocio(profesionalRequest.getIdNegocio()), lo comento porque el idNegocio viene por variable global en negocio controller
                .build();
    }


}

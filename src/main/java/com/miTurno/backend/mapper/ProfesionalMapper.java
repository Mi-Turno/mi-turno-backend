package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.entidad.CredencialEntidad;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.repositorio.NegocioRepositorio;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.repositorio.ServicioRepositorio;
import com.miTurno.backend.request.ProfesionalRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProfesionalMapper {

    private final RolRepositorio rolRepositorio;
    private final NegocioRepositorio negocioRepositorio;
    private final ServicioRepositorio servicioRepositorio;
    private final ServicioMapper servicioMapper;
    private final HorarioProfesionalMapper profesionalMapper;
    private final TurnoMapper turnoMapper;

    public ProfesionalMapper(RolRepositorio rolRepositorio, NegocioRepositorio negocioRepositorio, ServicioRepositorio servicioRepositorio,ServicioMapper servicioMapper,HorarioProfesionalMapper profesionalMapper,TurnoMapper turnoMapper) {
        this.rolRepositorio = rolRepositorio;
        this.negocioRepositorio = negocioRepositorio;
        this.servicioRepositorio = servicioRepositorio;
        this.servicioMapper = servicioMapper;
        this.profesionalMapper = profesionalMapper;
        this.turnoMapper = turnoMapper;
    }

    //request a entidad
    public ProfesionalEntidad toEntidad(Long idNegocio,ProfesionalRequest profesionalRequest){

        NegocioEntidad negocioEntidad= negocioRepositorio.findById(idNegocio).orElseThrow(()->new RecursoNoExisteException("id negocio"));
        RolEntidad rolEntidad = rolRepositorio.findByRol(profesionalRequest.getRolUsuario());

        CredencialEntidad credencialEntidad = CredencialEntidad.builder()
                .email(profesionalRequest.getEmail())
                .password(profesionalRequest.getPassword())
                .rolEntidad(rolEntidad)
                .telefono(profesionalRequest.getTelefono())
                .estado(true)
                .build();


        ProfesionalEntidad profesionalEntidad= ProfesionalEntidad.builder()
                .negocioEntidad(negocioEntidad)
                .nombre(profesionalRequest.getNombre())
                .apellido(profesionalRequest.getApellido())
                .fechaNacimiento(profesionalRequest.getFechaNacimiento())
                .credenciales(credencialEntidad)
                .build();
        negocioEntidad.getProfesionales().add(profesionalEntidad);
        return profesionalEntidad;
    }

    //entidad a modelo
    public Profesional toModel(ProfesionalEntidad profesionalEntidad){
        return Profesional.builder()
                .idUsuario(profesionalEntidad.getId())
                .email(profesionalEntidad.getCredencial().getEmail())
                .apellido(profesionalEntidad.getApellido())
                .nombre(profesionalEntidad.getNombre())
                .estado(profesionalEntidad.getCredencial().getEstado())
                .telefono(profesionalEntidad.getCredencial().getTelefono())
                .rolUsuario(profesionalEntidad.getCredencial().getRolEntidad().getRol())
                .password(profesionalEntidad.getCredencial().getPassword())
                .fechaNacimiento(profesionalEntidad.getFechaNacimiento())
                .idNegocio(profesionalEntidad.getNegocioEntidad().getId())
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


        return Profesional.builder()
                .email(profesionalRequest.getEmail())
                .apellido(profesionalRequest.getApellido())
                .nombre(profesionalRequest.getNombre())
                .estado(true)
                .telefono(profesionalRequest.getTelefono())
                .rolUsuario(profesionalRequest.getRolUsuario())
                .password(profesionalRequest.getPassword())
                .fechaNacimiento(profesionalRequest.getFechaNacimiento())
//                .idNegocio(profesionalRequest.getIdNegocio()), lo comento porque el idNegocio viene por variable global en negocio controller
                .build();
    }


}

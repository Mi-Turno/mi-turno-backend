package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.entidad.CredencialesEntidad;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.repositorio.NegocioRepositorio;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.repositorio.ServicioRepositorio;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfesionalMapper {

    private final RolRepositorio rolRepositorio;
    private final NegocioRepositorio negocioRepositorio;
    private final ServicioRepositorio servicioRepositorio;

    public ProfesionalMapper(RolRepositorio rolRepositorio, NegocioRepositorio negocioRepositorio, ServicioRepositorio servicioRepositorio) {
        this.rolRepositorio = rolRepositorio;
        this.negocioRepositorio = negocioRepositorio;
        this.servicioRepositorio = servicioRepositorio;
    }

    //request a entidad
    public ProfesionalEntidad toEntidad(Long idNegocio,ProfesionalRequest profesionalRequest){

        NegocioEntidad negocioEntidad= negocioRepositorio.findById(idNegocio).orElseThrow(()->new RecursoNoExisteException("id negocio"));
        RolEntidad rolEntidad = rolRepositorio.findById(profesionalRequest.getIdRol()).orElseThrow(()->new RecursoNoExisteException("id rol"));

        CredencialesEntidad credencialesEntidad = CredencialesEntidad.builder()
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
                .credenciales(credencialesEntidad)
                .build();
        negocioEntidad.getProfesionales().add(profesionalEntidad);
        return profesionalEntidad;
    }

    //entidad a modelo
    public Profesional toModel(ProfesionalEntidad profesionalEntidad){
        return Profesional.builder()
                .idUsuario(profesionalEntidad.getIdUsuario())
                .email(profesionalEntidad.getCredenciales().getEmail())
                .apellido(profesionalEntidad.getApellido())
                .nombre(profesionalEntidad.getNombre())
                .estado(profesionalEntidad.getCredenciales().getEstado())
                .telefono(profesionalEntidad.getCredenciales().getTelefono())
                .rolUsuario(profesionalEntidad.getCredenciales().getRolEntidad().getRol())
                .password(profesionalEntidad.getCredenciales().getPassword())
                .fechaNacimiento(profesionalEntidad.getFechaNacimiento())
                .idNegocio(profesionalEntidad.getNegocioEntidad().getIdUsuario())
                .build();
    }


    //request a modelo

    public Profesional toModel(ProfesionalRequest profesionalRequest){

        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(profesionalRequest.getIdRol()).get().getRol();

        return Profesional.builder()
                .email(profesionalRequest.getEmail())
                .apellido(profesionalRequest.getApellido())
                .nombre(profesionalRequest.getNombre())
                .estado(true)
                .telefono(profesionalRequest.getTelefono())
                .rolUsuario(rolUsuarioEnum)
                .password(profesionalRequest.getPassword())
                .fechaNacimiento(profesionalRequest.getFechaNacimiento())
//                .idNegocio(profesionalRequest.getIdNegocio()), lo comento porque el idNegocio viene por variable global en negocio controller
                .build();
    }

    public List<Profesional> toModel(List<ProfesionalEntidad> profesionalList) {
        return profesionalList.stream()
                .map(this::toModel) // Usa el método toModel(ProfesionalEntidad) para cada entidad en la lista
                .collect(Collectors.toList());
    }
}

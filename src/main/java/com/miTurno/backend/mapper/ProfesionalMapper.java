package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfesionalMapper {

    private final RolRepositorio rolRepositorio;

    public ProfesionalMapper(RolRepositorio rolRepositorio) {
        this.rolRepositorio = rolRepositorio;
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
                .idNegocio(profesionalRequest.getIdNegocio())
                .build();
    }

    public List<Profesional> toModel(List<ProfesionalEntidad> profesionalList) {
        return profesionalList.stream()
                .map(this::toModel) // Usa el método toModel(ProfesionalEntidad) para cada entidad en la lista
                .collect(Collectors.toList());
    }
}

package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.Credencial;
import com.miTurno.backend.entidad.CredencialEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.DTO.Negocio;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class NegocioMapper {

    private final RolRepositorio rolRepositorio;
    private final ProfesionalMapper profesionalMapper;
    private final ServicioMapper servicioMapper;
    private final CredencialMapper credencialMapper;

    public NegocioMapper(RolRepositorio rolRepositorio, ProfesionalMapper profesionalMapper, ServicioMapper servicioMapper, CredencialMapper credencialMapper) {
        this.rolRepositorio = rolRepositorio;
        this.profesionalMapper = profesionalMapper;
        this.servicioMapper = servicioMapper;
        this.credencialMapper = credencialMapper;
    }



    //request a negocio
    public Negocio toModel(NegocioRequest negocioRequest){

        Credencial credencial= credencialMapper.toModel(negocioRequest.getCrendeciales());

        return Negocio.builder()
                .credencial(credencial)

                .nombre(negocioRequest.getNombre())
                .apellido(negocioRequest.getApellido())
                .rolUsuario(negocioRequest.getRolUsuarioEnum())
                .fechaNacimiento(negocioRequest.getFechaNacimiento())
                .rubro(negocioRequest.getRubro())
                .calle(negocioRequest.getCalle())
                .altura(negocioRequest.getAltura())
                .detalle(negocioRequest.getDetalle())
                .build();
    }

    //negocio a Entidad
    public NegocioEntidad toEntidad(Negocio negocio){

        RolEntidad rolEntidad = RolEntidad.builder()
                .rol(negocio.getRolUsuario())
                .build();

        CredencialEntidad credencialEntidad = credencialMapper.toEntidad(negocio.getCredencial());


        NegocioEntidad negocioEntidad = new NegocioEntidad();

        negocioEntidad.setRolEntidad(rolEntidad);

        negocioEntidad.setRubro(negocio.getRubro());
        negocioEntidad.setNombre(negocio.getNombre());
        negocioEntidad.setApellido(negocio.getApellido());
        negocioEntidad.setCalle(negocio.getCalle());
        negocioEntidad.setAltura(negocio.getAltura());
        negocioEntidad.setDetalle(negocio.getDetalle());
        negocioEntidad.setFechaNacimiento(negocio.getFechaNacimiento());
        negocioEntidad.setCredencial(credencialEntidad);


        return negocioEntidad;
    }
    //entidad a modelo
    public Negocio toModel(NegocioEntidad negocioEntidad){

       return Negocio.builder()
               .idUsuario(negocioEntidad.getId())
               .email(negocioEntidad.getCredencial().getEmail())
               .password(negocioEntidad.getCredencial().getPassword())
               .telefono(negocioEntidad.getCredencial().getTelefono())
               .rolUsuario(negocioEntidad.getCredencial().getRolEntidad().getRol())
               .estado(negocioEntidad.getCredencial().getEstado())

               .nombre(negocioEntidad.getNombre())
               .apellido(negocioEntidad.getApellido())
               .fechaNacimiento(negocioEntidad.getFechaNacimiento())

               .rubro(negocioEntidad.getRubro())
               .calle(negocioEntidad.getCalle())
               .altura(negocioEntidad.getAltura())
               .detalle(negocioEntidad.getDetalle())

               .profesionales(profesionalMapper.toModelList(negocioEntidad.getProfesionales()))
               .servicios(servicioMapper.toModelList(negocioEntidad.getServicios()))
               .clientes(negocioEntidad.getClientes())
               .build();
    }
    public List<Negocio> toModelList(List<NegocioEntidad> listaNegociosEntidad) {
        // Si la lista es null, retorna una lista vacía en lugar de null
        if (listaNegociosEntidad == null) {
            return Collections.emptyList();
        }

        return listaNegociosEntidad.stream()
                .map(this::toModel)
                .toList();
    }
}

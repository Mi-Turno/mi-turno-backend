package com.miTurno.backend.mapper;

import com.miTurno.backend.model.Credencial;
import com.miTurno.backend.entidad.CredencialEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.model.Negocio;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class NegocioMapper {


    private final ProfesionalMapper profesionalMapper;
    private final ServicioMapper servicioMapper;
    private final CredencialMapper credencialMapper;

    public NegocioMapper(ProfesionalMapper profesionalMapper, ServicioMapper servicioMapper, CredencialMapper credencialMapper) {
        this.profesionalMapper = profesionalMapper;
        this.servicioMapper = servicioMapper;
        this.credencialMapper = credencialMapper;
    }



    //request a negocio
    public Negocio toModel(NegocioRequest negocioRequest){

        Credencial credencial= credencialMapper.toModel(negocioRequest.getCredencial());

        return Negocio.builder()
                .credencial(credencial)

                .nombre(negocioRequest.getNombre())
                .apellido(negocioRequest.getApellido())
                .rolUsuario(negocioRequest.getRolUsuario())
                .fechaNacimiento(negocioRequest.getFechaNacimiento())
                .rubro(negocioRequest.getRubro())
                .calle(negocioRequest.getCalle())
                .altura(negocioRequest.getAltura())
                .detalle(negocioRequest.getDetalle())
                .build();
    }

    //negocio a Entidad
    public NegocioEntidad toEntidad(Negocio negocio,RolEntidad rolEntidad){



        CredencialEntidad credencialEntidad = credencialMapper.toEntidad(negocio.getCredencial());


        return NegocioEntidad.builder()
                .rolEntidad(rolEntidad)
                .rubro(negocio.getRubro())
                .nombre(negocio.getNombre())
                .apellido(negocio.getApellido())
                .calle(negocio.getCalle())
                .altura(negocio.getAltura())
                .detalle(negocio.getDetalle())
                .fechaNacimiento(negocio.getFechaNacimiento())
                .credencial(credencialEntidad)
                .build();

    }
    //entidad a modelo
    public Negocio toModel(NegocioEntidad negocioEntidad){

        Credencial unaCredencial = Credencial.builder()
                .estado(negocioEntidad.getCredencial().getEstado())
                .email(negocioEntidad.getCredencial().getEmail())
                .idCredencial(negocioEntidad.getCredencial().getId())
                .telefono(negocioEntidad.getCredencial().getTelefono())
                .password(negocioEntidad.getCredencial().getPassword())
                .build();

       return Negocio.builder()
               .credencial(unaCredencial)

               .rolUsuario(negocioEntidad.getRolEntidad().getRol())
               .idUsuario(negocioEntidad.getId())
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

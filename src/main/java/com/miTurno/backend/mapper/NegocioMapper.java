package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.entidad.CredencialesEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.DTO.Negocio;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.stereotype.Component;

@Component
public class NegocioMapper {

    private final RolRepositorio rolRepositorio;
    private final ProfesionalMapper profesionalMapper;

    public NegocioMapper(RolRepositorio rolRepositorio,ProfesionalMapper profesionalMapper) {
        this.rolRepositorio = rolRepositorio;
        this.profesionalMapper = profesionalMapper;
    }

    //entidad a Negocio
    /*public Negocio toModel(NegocioEntidad negocioEntidad){

        return Negocio.builder()
                .idUsuario(negocioEntidad.getIdUsuario())
                .rubro(negocioEntidad.getRubro())
                .calle(negocioEntidad.getCalle())
                .altura(negocioEntidad.getAltura())
                .detalle(negocioEntidad.getDetalle())
                .build();
    }*/


    //request a negocio
    public Negocio toModel(NegocioRequest negocioRequest){
        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(negocioRequest.getIdRol()).get().getRol();

        return Negocio.builder()
                .email(negocioRequest.getEmail())
                .estado(true)
                .nombre(negocioRequest.getNombre())
                .apellido(negocioRequest.getApellido())
                .rolUsuario(rolUsuarioEnum)
                .password(negocioRequest.getPassword())
                .telefono(negocioRequest.getTelefono())
                .fechaNacimiento(negocioRequest.getFechaNacimiento())
                .rubro(negocioRequest.getRubro())
                .calle(negocioRequest.getCalle())
                .altura(negocioRequest.getAltura())
                .detalle(negocioRequest.getDetalle())
                .build();
    }

    //negocio a Entidad
    public NegocioEntidad toEntidad(Negocio negocio){

        RolEntidad rolEntidad = rolRepositorio.findByRol(negocio.getRolUsuario());

        CredencialesEntidad credencialesEntidad = CredencialesEntidad.builder()
                .rolEntidad(rolEntidad)
                .telefono(negocio.getTelefono())
                .estado(negocio.getEstado())
                .password(negocio.getPassword())
                .email(negocio.getEmail())
                .build();


        NegocioEntidad negocioEntidad = new NegocioEntidad();
        negocioEntidad.setRubro(negocio.getRubro());
        negocioEntidad.setNombre(negocio.getNombre());
        negocioEntidad.setApellido(negocio.getApellido());
        negocioEntidad.setCalle(negocio.getCalle());
        negocioEntidad.setAltura(negocio.getAltura());
        negocioEntidad.setDetalle(negocio.getDetalle());
        negocioEntidad.setFechaNacimiento(negocio.getFechaNacimiento());
        negocioEntidad.setCredenciales(credencialesEntidad);


        return negocioEntidad;
    }
    //entidad a modelo
    public Negocio toModel(NegocioEntidad negocioEntidad){
       return Negocio.builder()
               .idUsuario(negocioEntidad.getIdUsuario())
               .email(negocioEntidad.getCredenciales().getEmail())
               .password(negocioEntidad.getCredenciales().getPassword())
               .telefono(negocioEntidad.getCredenciales().getTelefono())
               .rolUsuario(negocioEntidad.getCredenciales().getRolEntidad().getRol())
               .estado(negocioEntidad.getCredenciales().getEstado())

               .nombre(negocioEntidad.getNombre())
               .apellido(negocioEntidad.getApellido())
               .fechaNacimiento(negocioEntidad.getFechaNacimiento())

               .rubro(negocioEntidad.getRubro())
               .calle(negocioEntidad.getCalle())
               .altura(negocioEntidad.getAltura())
               .detalle(negocioEntidad.getDetalle())

               .profesionales(profesionalMapper.toModelList(negocioEntidad.getProfesionales()))
               .servicios(negocioEntidad.getServicios())
               .clientes(negocioEntidad.getClientes())
               .build();
    }

}

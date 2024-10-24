package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.ServicioRequest;
import com.miTurno.backend.DTO.UsuarioRequest;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.modelo.Servicio;
import com.miTurno.backend.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    //entidad a usuario
    public Usuario toModel(UsuarioEntidad usuarioEntidad){
        return Usuario.builder()
                .idUsuario(usuarioEntidad.getIdUsuario())
                .rolUsuario(usuarioEntidad.getRolUsuarioEnum())
                .apellido(usuarioEntidad.getApellido())
                .celular(usuarioEntidad.getCelular())
                .correoElectronico(usuarioEntidad.getCorreoElectronico())
                .fechaNacimiento(usuarioEntidad.getFechaNacimiento())
                .nombre(usuarioEntidad.getNombre())
                .build();
    }

    //request a usuario
    public Usuario toModel(UsuarioRequest usuarioRequest){
        return Usuario.builder()
                .idUsuario(usuarioRequest.getIdUsuario())
                .rolUsuario(usuarioRequest.getRolUsuarioEnum())
                .apellido(usuarioRequest.getApellido())
                .celular(usuarioRequest.getCelular())
                .correoElectronico(usuarioRequest.getCorreoElectronico())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento())
                .nombre(usuarioRequest.getNombre())
                .build();
    }
}

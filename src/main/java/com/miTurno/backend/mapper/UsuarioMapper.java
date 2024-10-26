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
                .email(usuarioEntidad.getEmail())
                .fechaNacimiento(usuarioEntidad.getFechaNacimiento())
                .nombre(usuarioEntidad.getNombre())
                .password(usuarioEntidad.getPassword())
                .estado(usuarioEntidad.getEstado())
                .build();
    }

    //request a usuario
    public Usuario toModel(UsuarioRequest usuarioRequest){
        return Usuario.builder()
                .rolUsuario(usuarioRequest.getRol())
                .apellido(usuarioRequest.getApellido())
                .celular(usuarioRequest.getTelefono())
                .email(usuarioRequest.getEmail())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento())
                .nombre(usuarioRequest.getNombre())
                .password(usuarioRequest.getPassword())
                .estado(true)
                .build();
    }

    //usuario a entidad

    public UsuarioEntidad toEntidad(Usuario usuario){
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setNombre(usuario.getNombre());
        usuarioEntidad.setApellido(usuario.getApellido());
        usuarioEntidad.setEmail(usuario.getEmail());
        usuarioEntidad.setPassword(usuario.getPassword());
        usuarioEntidad.setCelular(usuario.getCelular());
        usuarioEntidad.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioEntidad.setRolUsuarioEnum(usuario.getRolUsuario());
        usuarioEntidad.setEstado(true);
        return usuarioEntidad;
    }

}

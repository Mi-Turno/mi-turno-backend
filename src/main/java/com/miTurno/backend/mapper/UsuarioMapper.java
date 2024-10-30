package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.UsuarioRequest;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final RolRepositorio rolRepositorio;

    @Autowired
    public UsuarioMapper(RolRepositorio rolRepositorio) {
        this.rolRepositorio = rolRepositorio;
    }

    //entidad a usuario
    public Usuario toModel(UsuarioEntidad usuarioEntidad){
        return Usuario.builder()
                .idUsuario(usuarioEntidad.getIdUsuario())
                .rolUsuario(usuarioEntidad.getRolEntidad().getRol())
                .apellido(usuarioEntidad.getApellido())
                .telefono(usuarioEntidad.getTelefono())
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
                .rolUsuario(usuarioRequest.getRolUsuarioEnum())
                .apellido(usuarioRequest.getApellido())
                .telefono(usuarioRequest.getTelefono())
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
        usuarioEntidad.setTelefono(usuario.getTelefono());
        usuarioEntidad.setFechaNacimiento(usuario.getFechaNacimiento());


        RolEntidad nuevoRol = rolRepositorio.findByRol(usuario.getRolUsuario());
        usuarioEntidad.setRolEntidad(nuevoRol);
        usuarioEntidad.setEstado(true);
        return usuarioEntidad;
    }

}

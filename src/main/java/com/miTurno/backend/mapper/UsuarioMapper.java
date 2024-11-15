package com.miTurno.backend.mapper;

import com.miTurno.backend.entidad.CredencialEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.request.UsuarioRequest;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.DTO.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    private final CredencialMapper credencialMapper;

    @Autowired
    public UsuarioMapper(CredencialMapper credencialMapper) {

        this.credencialMapper = credencialMapper;
    }

    //entidad a usuario
    public Usuario toModel(UsuarioEntidad usuarioEntidad){

        return Usuario.builder()
                .idUsuario(usuarioEntidad.getId())
                .rolUsuario(usuarioEntidad.getRolEntidad().getRol())
                .apellido(usuarioEntidad.getApellido())
                .fechaNacimiento(usuarioEntidad.getFechaNacimiento())
                .nombre(usuarioEntidad.getNombre())
                .credencial(credencialMapper.toModel(usuarioEntidad.getCredencial()))
                .build();
    }

    //request a usuario
    public Usuario toModel(UsuarioRequest usuarioRequest){

        return Usuario.builder()
                .rolUsuario(usuarioRequest.getRolUsuarioEnum())
                .apellido(usuarioRequest.getApellido())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento())
                .nombre(usuarioRequest.getNombre())
                .credencial(credencialMapper.toModel(usuarioRequest.getCrendeciales()))
                .build();
    }

    //usuario a entidad

    public UsuarioEntidad toEntidad(Usuario usuario){

        //creamos las credenciales
        CredencialEntidad credencialEntidad = credencialMapper.toEntidad(usuario.getCredencial());

        //rol
        RolEntidad rolEntidad= RolEntidad.builder()
                .rol(usuario.getRolUsuario())
                .build();

        //luego la entidad
        UsuarioEntidad usuarioEntidad= UsuarioEntidad.builder()
                        .id(usuario.getIdUsuario())
                        .credencial(credencialEntidad)
                        .rolEntidad(rolEntidad)
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .fechaNacimiento(usuario.getFechaNacimiento())
                        .build();

        credencialEntidad.setUsuario(usuarioEntidad);
        usuarioEntidad.setCredencial(credencialEntidad);

        return usuarioEntidad;
    }

}

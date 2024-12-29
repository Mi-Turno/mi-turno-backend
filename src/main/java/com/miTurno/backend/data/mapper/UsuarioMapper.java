package com.miTurno.backend.data.mapper;

import com.miTurno.backend.data.domain.CredencialEntidad;
import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.data.dtos.request.UsuarioRequest;
import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.dtos.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

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
                .rolUsuario(usuarioRequest.getRolUsuario())
                .apellido(usuarioRequest.getApellido())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento())
                .nombre(usuarioRequest.getNombre())
                .credencial(credencialMapper.toModel(usuarioRequest.getCredencial()))
                .build();
    }

    //usuario a entidad

    public UsuarioEntidad toEntidad(Usuario usuario,RolEntidad rolEntidad){

        //creamos las credenciales
        CredencialEntidad credencialEntidad = credencialMapper.toEntidad(usuario.getCredencial());

        //rol

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

    public List<Usuario> toModelList(List<UsuarioEntidad> listaUsuarioEntidad) {
        // Si la lista es null, retorna una lista vacía en lugar de null
        if (listaUsuarioEntidad == null) {
            return Collections.emptyList();
        }

        return listaUsuarioEntidad.stream()
                .map(this::toModel)
                .toList();
    }

}

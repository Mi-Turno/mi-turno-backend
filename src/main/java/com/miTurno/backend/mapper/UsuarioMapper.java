package com.miTurno.backend.mapper;

import com.miTurno.backend.entidad.CredencialesEntidad;
import com.miTurno.backend.repositorio.CredencialesRepositorio;
import com.miTurno.backend.request.UsuarioRequest;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.tipos.RolUsuarioEnum;
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
                .rolUsuario(usuarioEntidad.getCredenciales().getRolEntidad().getRol())
                .apellido(usuarioEntidad.getApellido())
                .telefono(usuarioEntidad.getTelefono())
                .email(usuarioEntidad.getCredenciales().getEmail())
                .fechaNacimiento(usuarioEntidad.getFechaNacimiento())
                .nombre(usuarioEntidad.getNombre())
                .estado(usuarioEntidad.getCredenciales().getEstado())
                .build();
    }

    //request a usuario
    public Usuario toModel(UsuarioRequest usuarioRequest){

        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(usuarioRequest.getIdRol()).get().getRol();

        return Usuario.builder()
                .rolUsuario(rolUsuarioEnum)
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

        RolEntidad rolEntidad= rolRepositorio.findByRol(usuario.getRolUsuario());

        //creamos primero las credenciales
        CredencialesEntidad credencialesEntidad =
                CredencialesEntidad.builder()
                        .rolEntidad(rolEntidad)
                        .email(usuario.getEmail())
                        .password(usuario.getPassword())
                        .estado(true)
                        .build();


        //luego la entidad
        return UsuarioEntidad.builder()
                        .credenciales(credencialesEntidad)
                        .nombre(usuario.getNombre())
                        .telefono(usuario.getTelefono())
                        .apellido(usuario.getApellido())
                        .fechaNacimiento(usuario.getFechaNacimiento())
                        .build();

    }

}

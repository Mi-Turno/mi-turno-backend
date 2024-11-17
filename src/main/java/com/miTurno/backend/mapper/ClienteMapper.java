package com.miTurno.backend.mapper;

import com.miTurno.backend.model.Cliente;
import com.miTurno.backend.entidad.*;
import com.miTurno.backend.request.UsuarioRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ClienteMapper {
    private final TurnoMapper turnoMapper;
    private final CredencialMapper credencialMapper;

    public ClienteMapper( TurnoMapper turnoMapper, CredencialMapper credencialMapper) {
        this.turnoMapper = turnoMapper;

        this.credencialMapper = credencialMapper;
    }

    //request a entidad
    public ClienteEntidad toEntidad(UsuarioRequest usuarioRequest,RolEntidad rolEntidad){


        CredencialEntidad unaCredencial= credencialMapper.toEntidad(credencialMapper.toModel(usuarioRequest.getCredencial()));


        return ClienteEntidad.builder()
                .rolEntidad(rolEntidad)
                .credencial(unaCredencial)
                .nombre(usuarioRequest.getNombre())
                .apellido(usuarioRequest.getApellido())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento())
                .listadoDeTurnos(new ArrayList<>())
                .build();
    }

    //request a modelo
    public Cliente toModel(UsuarioRequest usuarioRequest){

        return Cliente.builder()
                .credencial(credencialMapper.toModel(usuarioRequest.getCredencial()))
                .nombre(usuarioRequest.getNombre())
                .apellido(usuarioRequest.getApellido())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento())
                .turnos(new ArrayList<>())
                .build();
    }


    //Entidad a modelo
    public Cliente toModel(ClienteEntidad clienteEntidad){

        return Cliente.builder()
                .idUsuario(clienteEntidad.getId())
                .apellido(clienteEntidad.getApellido())
                .nombre(clienteEntidad.getNombre())
                .fechaNacimiento(clienteEntidad.getFechaNacimiento())
                .turnos(turnoMapper.toModelList(clienteEntidad.getListadoDeTurnos()))
                .build();
    }

}

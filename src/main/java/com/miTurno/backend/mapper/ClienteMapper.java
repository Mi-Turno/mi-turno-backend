package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.Cliente;
import com.miTurno.backend.DTO.Credencial;
import com.miTurno.backend.DTO.Turno;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.entidad.*;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.request.UsuarioRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteMapper {
    private final RolRepositorio rolRepositorio;
    private final TurnoMapper turnoMapper;
    private final UsuarioMapper usuarioMapper;
    private final CredencialMapper credencialMapper;

    public ClienteMapper(RolRepositorio rolRepositorio, TurnoMapper turnoMapper, UsuarioMapper usuarioMapper, CredencialMapper credencialMapper) {
        this.rolRepositorio = rolRepositorio;
        this.turnoMapper = turnoMapper;
        this.usuarioMapper = usuarioMapper;
        this.credencialMapper = credencialMapper;
    }

    //request a entidad
//    public ClienteEntidad toEntidad(UsuarioRequest usuarioRequest,CredencialEntidad credencialEntidad){
//
//        ClienteEntidad clienteEntidad = new ClienteEntidad();
//        clienteEntidad.setCredenciales(credencialEntidad);
//        clienteEntidad.setNombre(usuarioRequest.getNombre());
//        clienteEntidad.setApellido(usuarioRequest.getApellido());
//        clienteEntidad.setFechaNacimiento(usuarioRequest.getFechaNacimiento());
//
//        //clienteEntidad.getListadoDeTurnos()
//
//        return clienteEntidad;
//    }

    //request a modelo
    public Cliente toModel(UsuarioRequest usuarioRequest){

        Cliente cliente = new Cliente();
        cliente.setCredencial(credencialMapper.toModel(usuarioRequest.getCrendeciales()));
        cliente.setNombre(usuarioRequest.getNombre());
        cliente.setApellido(usuarioRequest.getApellido());
        cliente.setFechaNacimiento(usuarioRequest.getFechaNacimiento());
        cliente.setTurnos(new ArrayList<>());

        return cliente;
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

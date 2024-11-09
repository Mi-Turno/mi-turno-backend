package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.Cliente;
import com.miTurno.backend.DTO.Turno;
import com.miTurno.backend.entidad.*;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.request.UsuarioRequest;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    private final RolRepositorio rolRepositorio;
    private final TurnoMapper turnoMapper;

    public ClienteMapper(RolRepositorio rolRepositorio, TurnoMapper turnoMapper) {
        this.rolRepositorio = rolRepositorio;
        this.turnoMapper = turnoMapper;
    }

    //request a entidad
    public ClienteEntidad toEntidad(UsuarioRequest usuarioRequest){

        RolEntidad rolEntidad = rolRepositorio.findById(usuarioRequest.getIdRolUsuario()).orElseThrow(()->new RecursoNoExisteException("id rol"));

        CredencialesEntidad credencialesEntidad = CredencialesEntidad.builder()
                .email(usuarioRequest.getEmail())
                .password(usuarioRequest.getPassword())
                .rolEntidad(rolEntidad)
                .telefono(usuarioRequest.getTelefono())
                .estado(true)
                .build();

        ClienteEntidad clienteEntidad = new ClienteEntidad();
        clienteEntidad.setCredenciales(credencialesEntidad);
        clienteEntidad.setNombre(usuarioRequest.getNombre());
        clienteEntidad.setApellido(usuarioRequest.getApellido());
        clienteEntidad.setFechaNacimiento(usuarioRequest.getFechaNacimiento());

        return clienteEntidad;
    }

    //Entidad a modelo
    public Cliente toModel(ClienteEntidad clienteEntidad){

        return Cliente.builder()
                .idUsuario(clienteEntidad.getIdUsuario())
                .email(clienteEntidad.getCredenciales().getEmail())
                .apellido(clienteEntidad.getApellido())
                .nombre(clienteEntidad.getNombre())
                .estado(clienteEntidad.getCredenciales().getEstado())
                .telefono(clienteEntidad.getCredenciales().getTelefono())
                .idRolUsuario(clienteEntidad.getCredenciales().getRolEntidad().getRol())
                .password(clienteEntidad.getCredenciales().getPassword())
                .fechaNacimiento(clienteEntidad.getFechaNacimiento())
                .turnos(turnoMapper.toModelList(clienteEntidad.getListadoDeTurnos()))
                .build();
    }

}

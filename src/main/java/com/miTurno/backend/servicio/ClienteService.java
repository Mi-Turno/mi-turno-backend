package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Cliente;
import com.miTurno.backend.entidad.ClienteEntidad;
import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.mapper.ClienteMapper;
import com.miTurno.backend.repositorio.*;
import com.miTurno.backend.request.UsuarioRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepositorio clienteRepositorio;
    private final RolRepositorio rolRepositorio;
    private final CredencialesRepositorio credencialesRepositorio;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteService(ClienteRepositorio clienteRepositorio, RolRepositorio rolRepositorio, CredencialesRepositorio credencialesRepositorio, ClienteMapper clienteMapper) {
        this.clienteRepositorio = clienteRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.credencialesRepositorio = credencialesRepositorio;
        this.clienteMapper = clienteMapper;
    }



    //Crear un cliente
    public Cliente crearUnCliente(UsuarioRequest usuarioRequest) throws RolIncorrectoException, RecursoNoExisteException {

        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(usuarioRequest.getIdRolUsuario()).get().getRol();

        if (rolUsuarioEnum != RolUsuarioEnum.CLIENTE) {
            throw new RolIncorrectoException(RolUsuarioEnum.CLIENTE, rolUsuarioEnum);
        }
        if (credencialesRepositorio.findByEmail(usuarioRequest.getEmail()).isPresent()) {
            throw new EmailYaExisteException(usuarioRequest.getEmail());
        }
        if (credencialesRepositorio.findByTelefono(usuarioRequest.getTelefono()).isPresent()) {
            throw new TelefonoYaExisteException(usuarioRequest.getTelefono());
        }
        // Crear el cliente
        ClienteEntidad clienteEntidad = clienteMapper.toEntidad(usuarioRequest);
        return clienteMapper.toModel(clienteRepositorio.save(clienteEntidad)) ;
    }
// Obtener todos los clientes de un negocio

// Obtener un cliente por ID
    public Cliente buscarUsuario(Long id){
        return clienteMapper.toModel(clienteRepositorio.findById(id).orElseThrow(()->new UsuarioNoExistenteException(id)));
    }

}

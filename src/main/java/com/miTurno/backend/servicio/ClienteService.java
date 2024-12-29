package com.miTurno.backend.servicio;

import com.miTurno.backend.data.repositorio.ClienteRepositorio;
import com.miTurno.backend.data.repositorio.CredencialesRepositorio;
import com.miTurno.backend.data.repositorio.RolRepositorio;
import com.miTurno.backend.data.dtos.model.Cliente;
import com.miTurno.backend.data.dtos.model.Turno;
import com.miTurno.backend.data.domain.ClienteEntidad;
import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.excepciones.*;
import com.miTurno.backend.data.mapper.ClienteMapper;
import com.miTurno.backend.data.mapper.TurnoMapper;
import com.miTurno.backend.data.dtos.request.UsuarioRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepositorio clienteRepositorio;
    private final RolRepositorio rolRepositorio;
    private final CredencialesRepositorio credencialesRepositorio;
    private final ClienteMapper clienteMapper;
    private final TurnoMapper turnoMapper;

    @Autowired
    public ClienteService(ClienteRepositorio clienteRepositorio, RolRepositorio rolRepositorio, CredencialesRepositorio credencialesRepositorio, ClienteMapper clienteMapper, TurnoMapper turnoMapper) {
        this.clienteRepositorio = clienteRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.credencialesRepositorio = credencialesRepositorio;
        this.clienteMapper = clienteMapper;
        this.turnoMapper = turnoMapper;
    }


    //get listado de turnos del cliente
    public List<Turno> obtenerListadoDeTurnosPorId(Long idCliente) throws EntityNotFoundException {
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(idCliente)
                .orElseThrow(()->new EntityNotFoundException("Usuario con id: "+idCliente+" no encontrado."));

        return turnoMapper.toModelList(clienteEntidad.getListadoDeTurnos());
    }

    //Crear un cliente
    public Cliente crearUnCliente(UsuarioRequest usuarioRequest) throws RolIncorrectoException,EmailYaExisteException,TelefonoYaExisteException {


        if (usuarioRequest.getRolUsuario() != RolUsuarioEnum.CLIENTE) {
            throw new RolIncorrectoException(RolUsuarioEnum.CLIENTE, usuarioRequest.getRolUsuario());
        }
        if (credencialesRepositorio.findByEmail(usuarioRequest.getCredencial().getEmail()).isPresent()) {
            throw new EmailYaExisteException(usuarioRequest.getCredencial().getEmail());
        }
        if (credencialesRepositorio.findByTelefono(usuarioRequest.getCredencial().getTelefono()).isPresent()) {
            throw new TelefonoYaExisteException(usuarioRequest.getCredencial().getTelefono());
        }

        //buscamos el rol en el repositorio de roles

        RolEntidad rolEntidad = rolRepositorio.findByRol(usuarioRequest.getRolUsuario());

        // Crear el cliente
        ClienteEntidad clienteEntidad = clienteMapper.toEntidad(usuarioRequest,rolEntidad);
        return clienteMapper.toModel(clienteRepositorio.save(clienteEntidad)) ;
    }
// Obtener cliente by email and password para el login
public Cliente obtenerClienteByEmailAndPassword(String email, String password) throws EntityNotFoundException{
    return clienteMapper.toModel(clienteRepositorio.findByCredencial_EmailAndCredencial_Password(email,password));
}

// Obtener un cliente por ID
    public Cliente buscarCliente(Long id){
        return clienteMapper.toModel(clienteRepositorio.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Cliente con id: "+ id+" no encontrado.")));
    }

    //Put Cliente
    public Cliente actualizarClientePorId(Long id, UsuarioRequest clienteActualizado) throws EntityNotFoundException {

        //obtengo el cliente del repositorio
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cliente con id: "+ id+" no encontrado.")));




        clienteEntidad.setNombre(clienteActualizado.getNombre());
        clienteEntidad.setApellido(clienteActualizado.getApellido());

        //Actualizar credenciales
        clienteEntidad.getCredencial().setEmail(clienteActualizado.getCredencial().getEmail());
        clienteEntidad.getCredencial().setPassword((clienteActualizado.getCredencial().getPassword()));
        clienteEntidad.getCredencial().setTelefono(clienteActualizado.getCredencial().getTelefono());

        clienteEntidad= clienteRepositorio.save(clienteEntidad);

        return clienteMapper.toModel(clienteEntidad);
    }

    //Delete Cliente
    public Boolean eliminarClientePorId(Long id){
        Boolean rta = false;//
        if(clienteRepositorio.existsById(id)){
            ClienteEntidad clienteEntidad= clienteRepositorio.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Cliente con id: "+ id+" no encontrado."));

            clienteEntidad.getCredencial().setEstado(false);

            clienteRepositorio.save(clienteEntidad);
            rta = true;
        }
        return rta;
    }


}

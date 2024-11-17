package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Cliente;
import com.miTurno.backend.DTO.Turno;
import com.miTurno.backend.entidad.ClienteEntidad;
import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.mapper.ClienteMapper;
import com.miTurno.backend.mapper.TurnoMapper;
import com.miTurno.backend.repositorio.*;
import com.miTurno.backend.request.UsuarioRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
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
    public List<Turno> obtenerListadoDeTurnosPorId(Long idCliente){
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(idCliente).orElseThrow(()->new UsuarioNoExistenteException(idCliente));

        return turnoMapper.toModelList(clienteEntidad.getListadoDeTurnos());
    }

    //Crear un cliente
    public Cliente crearUnCliente(UsuarioRequest usuarioRequest) throws RolIncorrectoException, RecursoNoExisteException {


        if (usuarioRequest.getRolUsuarioEnum() != RolUsuarioEnum.CLIENTE) {
            throw new RolIncorrectoException(RolUsuarioEnum.CLIENTE, usuarioRequest.getRolUsuarioEnum());
        }
        if (credencialesRepositorio.findByEmail(usuarioRequest.getCredencial().getEmail()).isPresent()) {
            throw new EmailYaExisteException(usuarioRequest.getCredencial().getEmail());
        }
        if (credencialesRepositorio.findByTelefono(usuarioRequest.getCredencial().getTelefono()).isPresent()) {
            throw new TelefonoYaExisteException(usuarioRequest.getCredencial().getTelefono());
        }
        // Crear el cliente
        ClienteEntidad clienteEntidad = clienteMapper.toEntidad(usuarioRequest);
        return clienteMapper.toModel(clienteRepositorio.save(clienteEntidad)) ;
    }
// Obtener cliente by email and password para el login
public Cliente obtenerClienteByEmailAndPassword(String email, String password)throws UsuarioNoExistenteException{
    return clienteMapper.toModel(clienteRepositorio.findByCredencial_EmailAndCredencial_Password(email,password));
}

// Obtener un cliente por ID
    public Cliente buscarCliente(Long id){
        return clienteMapper.toModel(clienteRepositorio.findById(id).orElseThrow(()->new UsuarioNoExistenteException(id)));
    }

    //Put Cliente
    public Cliente actualizarClientePorId(Long id, Cliente actualizado) throws UsuarioNoExistenteException {

        ClienteEntidad clienteEntidad = clienteRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));

        //todo refactorizar para mejorar la calidad del codigo
        clienteEntidad.setNombre(actualizado.getNombre());
        clienteEntidad.setApellido(actualizado.getApellido());

        //Actualizar credenciales
        clienteEntidad.getCredencial().setEmail(actualizado.getCredencial().getEmail());
        clienteEntidad.getCredencial().setPassword((actualizado.getCredencial().getPassword()));
        clienteEntidad.getCredencial().setTelefono(actualizado.getCredencial().getTelefono());

        clienteEntidad= clienteRepositorio.save(clienteEntidad);

        return clienteMapper.toModel(clienteEntidad);
    }

    //Delete Cliente
    public Boolean eliminarClientePorId(Long id){
        Boolean rta = false;//
        if(clienteRepositorio.existsById(id)){
            ClienteEntidad clienteEntidad= clienteRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));

            clienteEntidad.getCredencial().setEstado(false);

            clienteRepositorio.save(clienteEntidad);
            rta = true;
        }
        return rta;
    }


}

package com.miTurno.backend.servicio;

import com.miTurno.backend.data.repositorio.ClienteRepositorio;
import com.miTurno.backend.data.repositorio.CredencialesRepositorio;
import com.miTurno.backend.data.repositorio.RolRepositorio;
import com.miTurno.backend.data.dtos.response.Cliente;
import com.miTurno.backend.data.dtos.response.Turno;
import com.miTurno.backend.data.domain.ClienteEntidad;
import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.excepciones.*;
import com.miTurno.backend.data.mapper.ClienteMapper;
import com.miTurno.backend.data.mapper.TurnoMapper;
import com.miTurno.backend.data.dtos.request.UsuarioRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepositorio clienteRepositorio;
    private final RolRepositorio rolRepositorio;
    private final CredencialesRepositorio credencialesRepositorio;
    private final ClienteMapper clienteMapper;
    private final TurnoMapper turnoMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Autowired
    public ClienteService(ClienteRepositorio clienteRepositorio, RolRepositorio rolRepositorio, CredencialesRepositorio credencialesRepositorio, ClienteMapper clienteMapper, TurnoMapper turnoMapper, PasswordEncoder passwordEncoder, AuthService authService) {
        this.clienteRepositorio = clienteRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.credencialesRepositorio = credencialesRepositorio;
        this.clienteMapper = clienteMapper;
        this.turnoMapper = turnoMapper;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }


    //get listado de turnos del cliente
    public List<Turno> obtenerListadoDeTurnosPorId(Long idCliente) throws EntityNotFoundException {
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(idCliente)
                .orElseThrow(()->new EntityNotFoundException("Usuario con id: "+idCliente+" no encontrado."));

        return turnoMapper.toModelList(clienteEntidad.getListadoDeTurnos());
    }

    //Crear un cliente
    public Cliente crearUnCliente(UsuarioRequest usuarioRequest) throws RolIncorrectoException, EntityExistsException, MessagingException {


        if (usuarioRequest.getRolUsuario() != RolUsuarioEnum.CLIENTE) {
            throw new RolIncorrectoException(RolUsuarioEnum.CLIENTE, usuarioRequest.getRolUsuario());
        }
        if (credencialesRepositorio.findByEmail(usuarioRequest.getCredencial().getEmail()).isPresent()) {
            throw new EntityExistsException("El usuario con el email: "+ usuarioRequest.getCredencial().getEmail() +" ya existe.");
        }
        if (credencialesRepositorio.findByTelefono(usuarioRequest.getCredencial().getTelefono()).isPresent()) {
            throw new EntityExistsException("El usuario con el telefono: "+ usuarioRequest.getCredencial().getTelefono() +" ya existe.");
        }

        //buscamos el rol en el repositorio de roles
        RolEntidad rolEntidad = rolRepositorio.findByRol(usuarioRequest.getRolUsuario());

        // Encriptamos password
        usuarioRequest.getCredencial().setPassword(passwordEncoder.encode(usuarioRequest.getCredencial().getPassword()));

        // Crear el cliente
        ClienteEntidad clienteEntidad = clienteMapper.toEntidad(usuarioRequest,rolEntidad);

        //todo verificacion modularizar

        //estado en false debido a que no esta verificado
        clienteEntidad.getCredencial().setEstado(false);

        clienteEntidad.getCredencial().setCodigoVerificacion(authService.generarCodigoDeVerificacion());
        clienteEntidad.getCredencial().setVencimientoCodigoVerificacion(LocalDateTime.now().plusMinutes(15));

        authService.enviarMailDeVerificacion(clienteEntidad);


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
                .orElseThrow(()-> new EntityNotFoundException("Cliente con id: "+ id+" no encontrado."));




        clienteEntidad.setNombre(clienteActualizado.getNombre());
        clienteEntidad.setApellido(clienteActualizado.getApellido());

        //Actualizar credenciales
        clienteEntidad.getCredencial().setEmail(clienteActualizado.getCredencial().getEmail());
        clienteEntidad.getCredencial().setPassword((clienteActualizado.getCredencial().getPassword()));
        clienteEntidad.getCredencial().setTelefono(clienteActualizado.getCredencial().getTelefono());

        clienteEntidad= clienteRepositorio.save(clienteEntidad);

        return clienteMapper.toModel(clienteEntidad);
    }
    //patch cliente
    public Cliente actualizarParcial(Long id, Cliente clienteParcial) {
        Optional<ClienteEntidad> clienteExistente = clienteRepositorio.findById(id);

        if (clienteExistente.isPresent()) {
            ClienteEntidad cliente = clienteExistente.get();

            // Actualiza solo las propiedades que llegan en el clienteParcial
            if (clienteParcial.getCredencial() != null && clienteParcial.getCredencial().getEmail() != null) {
                cliente.getCredencial().setEmail(clienteParcial.getCredencial().getEmail());
            }

            if (clienteParcial.getNombre() != null) {
                cliente.setNombre(clienteParcial.getNombre());
            }

            if (clienteParcial.getApellido() != null) {
                cliente.setApellido(clienteParcial.getApellido());
            }

            if (clienteParcial.getFechaNacimiento() != null) {
                cliente.setFechaNacimiento(clienteParcial.getFechaNacimiento());
            }

            return clienteMapper.toModel(clienteRepositorio.save(cliente));
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
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

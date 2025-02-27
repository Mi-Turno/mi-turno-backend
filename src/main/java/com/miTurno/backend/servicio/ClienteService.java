package com.miTurno.backend.servicio;

import com.miTurno.backend.data.dtos.request.CredencialRequest;
import com.miTurno.backend.data.dtos.response.Credencial;
import com.miTurno.backend.data.mapper.CredencialMapper;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClienteService {

    private final ClienteRepositorio clienteRepositorio;
    private final RolRepositorio rolRepositorio;
    private final CredencialesRepositorio credencialesRepositorio;
    private final ClienteMapper clienteMapper;
    private final TurnoMapper turnoMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final EnviarCorreoService enviarCorreoService;
    private final AtomicLong contadorEmail = new AtomicLong(1); // Inicialización del contador

    @Autowired
    public ClienteService(ClienteRepositorio clienteRepositorio, RolRepositorio rolRepositorio, CredencialesRepositorio credencialesRepositorio, ClienteMapper clienteMapper, TurnoMapper turnoMapper, PasswordEncoder passwordEncoder, AuthService authService, EnviarCorreoService enviarCorreoService) {
        this.clienteRepositorio = clienteRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.credencialesRepositorio = credencialesRepositorio;
        this.clienteMapper = clienteMapper;
        this.turnoMapper = turnoMapper;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.enviarCorreoService = enviarCorreoService;
    }


    //get listado de turnos del cliente
    public List<Turno> obtenerListadoDeTurnosPorId(Long idCliente) throws EntityNotFoundException {
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(idCliente)
                .orElseThrow(()->new EntityNotFoundException("Usuario con id: "+idCliente+" no encontrado."));

        return turnoMapper.toModelList(clienteEntidad.getListadoDeTurnos());
    }

    //get email de un cliente
    public String obtenerEmailPorId(Long idCliente){
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(idCliente)
                .orElseThrow(()->new EntityNotFoundException("Usuario con id: "+idCliente+" no encontrado."));

        return clienteEntidad.getCredencial().getEmail();
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


        clienteEntidad.getCredencial().setEstado(true);
        clienteEntidad.getCredencial().setUsuarioVerificado(false);
        clienteEntidad.getCredencial().setCodigo(authService.generarCodigoDeVerificacion());
        clienteEntidad.getCredencial().setVencimientoCodigo(LocalDateTime.now().plusMinutes(15));

        enviarCorreoService.enviarMailDeVerificacion(clienteEntidad);

        ClienteEntidad clienteGuardado= clienteRepositorio.save(clienteEntidad);

        return clienteMapper.toModel(clienteGuardado) ;
    }

    //Crear un cliente invitado
public Cliente crearUsuarioInvitado(String nombreCliente, String nombreNegocio){

        String nombreNegocioFinal =  nombreNegocio.replace(" ", "_");

        String email = "invitado@" + nombreNegocioFinal + contadorEmail.getAndIncrement() + ".miturno";


        UsuarioRequest usuarioRequest = UsuarioRequest.builder()
                .nombre(nombreCliente)
                .apellido("Invitado")
                .fechaNacimiento(LocalDate.now())
                .credencial(CredencialRequest.builder()
                        .telefono("00000" + contadorEmail)
                        .email(email)
                        .password(passwordEncoder.encode("invitado"))
                        .build())
                .rolUsuario(RolUsuarioEnum.CLIENTE)
                .build();


        RolEntidad rolEntidad = rolRepositorio.findByRol(usuarioRequest.getRolUsuario());
        ClienteEntidad clienteEntidad = clienteMapper.toEntidad(usuarioRequest,rolEntidad);
        ClienteEntidad clienteGuardado= clienteRepositorio.save(clienteEntidad);
        return clienteMapper.toModel(clienteGuardado);
}


    //Obtener el último cliente invitado por negocio
    public Cliente getLastClienteInvitadoByNegocio(String nombreNegocio){
        String nombreNegocioFinal =  nombreNegocio.replace(" ", "_");
        return clienteMapper.toModel(clienteRepositorio.findFirstByCredencial_EmailContainingOrderByIdDesc("invitado@" + nombreNegocioFinal));
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

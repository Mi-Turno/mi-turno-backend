package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.ClienteEntidad;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.repositorio.ClienteRepositorio;
import com.miTurno.backend.repositorio.NegocioRepositorio;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.request.UsuarioRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

//    private final NegocioRepositorio negocioRepositorio;
//    private final UsuarioRepositorio usuarioRepositorio;
//    ClienteRepositorio clienteRepositorio;
//
//    @Autowired
//    public ClienteService(ClienteRepositorio clienteRepositorio, NegocioRepositorio negocioRepositorio, UsuarioRepositorio usuarioRepositorio) {
//        this.clienteRepositorio = clienteRepositorio;
//        this.negocioRepositorio = negocioRepositorio;
//        this.usuarioRepositorio = usuarioRepositorio;
//    }
//
//    // Agregar un cliente a un negocio
//    public ClienteEntidad agregarCliente(Long idNegocio, UsuarioRequest clienteRequest) {
//
//
//        //Si el negocio no existe tiro excepcion
//        NegocioEntidad negocio = negocioRepositorio.findById(idNegocio)
//                .orElseThrow(() -> new RecursoNoExisteException("Negocio no encontrado"));
//
//
//
//        ClienteEntidad nuevoCliente = new ClienteEntidad();
//        nuevoCliente.setNombre(clienteRequest.getNombre());
//        nuevoCliente.setApellido(clienteRequest.getApellido());
//        nuevoCliente.setTelefono(clienteRequest.getTelefono());
//        nuevoCliente.setFechaNacimiento(clienteRequest.getFechaNacimiento());
//        nuevoCliente.setCredenciales(clienteRequest.getCredenciales()); // Ajusta según tu implementación
//
//        // Aquí se relaciona el cliente con el negocio
//        nuevoCliente.setNegocio(negocio); // Asegúrate de tener este método en ClienteEntidad
//
//        return clienteRepositorio.save(nuevoCliente);
//    }
//
//    // Obtener todos los clientes de un negocio
//    public List<ClienteEntidad> obtenerClientesPorNegocio(Long idNegocio) {
//        // Aquí debes implementar un método en tu repositorio que recupere
//        // los clientes según el ID del negocio
//        return clienteRepositorio.findAllByNegocioId(idNegocio);
//    }
//
//    // Obtener un cliente de un negocio por ID
//    public ClienteEntidad obtenerClientePorId(Long idNegocio, Long idCliente) {
//        // Primero verifica si el negocio existe
//        NegocioEntidad negocio = negocioRepositorio.findById(idNegocio)
//                .orElseThrow(() -> new ResourceNotFoundException("Negocio no encontrado"));
//
//        // Luego busca el cliente por ID y verifica que pertenezca al negocio
//        return clienteRepositorio.findByIdAndNegocioId(idCliente, idNegocio)
//                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado en este negocio"));
//    }

}

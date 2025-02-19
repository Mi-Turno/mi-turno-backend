package com.miTurno.backend.servicio;
import com.miTurno.backend.data.domain.*;
import com.miTurno.backend.data.dtos.response.personalizados.ClienteTablaResponse;
import com.miTurno.backend.data.mapper.ClienteMapper;
import com.miTurno.backend.data.repositorio.*;
import com.miTurno.backend.excepciones.*;
import com.miTurno.backend.data.dtos.request.NegocioRequest;
import com.miTurno.backend.data.mapper.NegocioMapper;
import com.miTurno.backend.data.dtos.response.Negocio;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NegocioService {

    //atributos
    private final NegocioRepositorio negocioRepositorio;
    private final NegocioMapper negocioMapper;
    private final RolRepositorio rolRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    private final TurnoRepositorio turnoRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final ClienteMapper clienteMapper;
    private final MetodosDePagoRepositorio metodosDePagoRepositorio;

    //constructores
    @Autowired
    public NegocioService(NegocioRepositorio negocioRepositorio, NegocioMapper negocioMapper, RolRepositorio rolRepositorio, PasswordEncoder passwordEncoder, AuthService authService, TurnoRepositorio turnoRepositorio, ClienteRepositorio clienteService, ClienteMapper clienteMapper, MetodosDePagoRepositorio metodosDePagoRepositorio) {
        this.negocioRepositorio = negocioRepositorio;
        this.negocioMapper = negocioMapper;
        this.rolRepositorio = rolRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.turnoRepositorio = turnoRepositorio;
        this.clienteRepositorio = clienteService;
        this.clienteMapper = clienteMapper;
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
    }

    //metodos

    //GET all negocios
    public List<Negocio> listarTodosLosNegocios(){
        return negocioMapper.toModelList(negocioRepositorio.findAll()) ;
    }



    //GET negocio x id
    public Negocio obtenerNegocioPorId(Long idNegocio) throws EntityNotFoundException{

        return negocioMapper.toModel(negocioRepositorio.findById(idNegocio)
                .orElseThrow(()-> new EntityNotFoundException("Negocio con id: "+ idNegocio+" no encontrado.")));
    }

    //obtener negocio x nombre
    public Negocio obtenerNegocioPorNombre(String nombre) throws EntityNotFoundException {

       return negocioMapper.toModel(negocioRepositorio.getNegocioEntidadByNombreIgnoreCase(nombre)
               .orElseThrow(()->new EntityNotFoundException("El negocio con el nombre: "+nombre+" no fue encontrado.")));
    }
    public Long obtenerNumeroDeUnNegocioById (Long idNegocio) {
        NegocioEntidad negocioEntidad= negocioRepositorio.getNegocioEntidadById(idNegocio);//.orElseThrow(()-> new EntityNotFoundException("El negocio con id: "+idNegocio+" no fue encontrado."));

        return Long.parseLong(negocioEntidad.getCredencial().getTelefono());
    }

    /**
     *1-Si tengo un turno quiere decir que soy o fui cliente
     *2-Obtengo los clientes y los retorno
     * @param idNegocio
     * @return List<ClienteTablaResponse>
     */
    //todo hacer response para poder retornar toda la informacion
    public List<ClienteTablaResponse> obtenerClientesPorNegocioId(Long idNegocio) {
        List<TurnoEntidad> listaDeTurnos = turnoRepositorio.findAllByNegocioEntidadId(idNegocio);
        List<ClienteTablaResponse> listaClientes = new ArrayList<>();
        // Usamos un HashSet con llave compuesta: "idNegocio-idCliente"
        Set<String> llavesAgregadas = new HashSet<>();

        for (TurnoEntidad turnoEntidad : listaDeTurnos) {
            Long idCliente = turnoEntidad.getClienteEntidad().getId();
            // LLave compuesta para que no se repita el id en un mismo negocio
            // esto permitirá diferenciar si en otro contexto se consultan turnos de distintos negocios.
            String llave = idNegocio + "-" + idCliente;

            if (!llavesAgregadas.contains(llave)) {
                llavesAgregadas.add(llave);
                ClienteEntidad clienteEntidad = clienteRepositorio.findById(idCliente)
                        .orElseThrow(() -> new EntityNotFoundException("Cliente con id: "+ idCliente+" no encontrado."));
                ClienteTablaResponse nuevoCliente = crearClienteTablaResponse(clienteEntidad);
                listaClientes.add(nuevoCliente);
            }
        }

        System.out.println(listaClientes);
        return listaClientes;
    }
    private ClienteTablaResponse crearClienteTablaResponse(ClienteEntidad cliente){
        return ClienteTablaResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .correo(cliente.getCredencial().getEmail())
                .telefono(cliente.getCredencial().getTelefono())
                .rol(cliente.getRolEntidad().getRol().toString())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .estado(cliente.getCredencial().getEstado())
                .build();

    }
    //POST negocio
    public Negocio crearUnNegocio(NegocioRequest negocioRequest)
            throws RolIncorrectoException, EntityExistsException, MessagingException {


        String nombreNegocio = negocioRequest.getNombre();

        if (negocioRequest.getRolUsuario() != RolUsuarioEnum.NEGOCIO) {
            throw new RolIncorrectoException(RolUsuarioEnum.NEGOCIO, negocioRequest.getRolUsuario());
        }
        //todo: antes había un negocioRequest.getRolUsuario() - En caso de que solucionen algo y esto les de problema

        if (negocioRepositorio.existsByNombreAndRolEntidad_Rol(nombreNegocio,negocioRequest.getRolUsuario())) {
            throw new EntityExistsException("El negocio cono el nombre: "+nombreNegocio+" ya existe.");
        }

        if(negocioRepositorio.existsByCredencial_Email(negocioRequest.getCredencial().getEmail())){
            throw new EntityExistsException("El negocio con el email: "+negocioRequest.getCredencial().getEmail()+" ya existe");
        }

        if(negocioRepositorio.existsByCredencial_Telefono(negocioRequest.getCredencial().getTelefono())){
            throw new EntityExistsException("El negocio con el telefono: "+negocioRequest.getCredencial().getTelefono()+" ya existe.");
        }

        //encriptamos la pswd
        negocioRequest.getCredencial().setPassword(passwordEncoder.encode(negocioRequest.getCredencial().getPassword()));

        //obtenemos el rol negocio
        RolEntidad rolEntidad = rolRepositorio.findByRol(negocioRequest.getRolUsuario());

        //pasamos a entidad
        NegocioEntidad negocio= negocioMapper.toEntidad(negocioMapper.toModel(negocioRequest),rolEntidad);

        negocio.getCredencial().setEstado(true);

        negocio.getCredencial().setCodigoVerificacion(authService.generarCodigoDeVerificacion());
        negocio.getCredencial().setVencimientoCodigoVerificacion(LocalDateTime.now().plusMinutes(15));

        authService.enviarMailDeVerificacion(negocio);

        return negocioMapper.toModel(negocioRepositorio.save(negocio));
    }

    //GET id negocio x nombre negocio
    public Long obtenerIdNegocioPorNombreNegocio(String nombreNegocio) throws EntityNotFoundException{

        NegocioEntidad negocioEntidad= negocioRepositorio.getNegocioEntidadByNombreIgnoreCase(nombreNegocio)
                .orElseThrow(()-> new EntityNotFoundException("El negocio con el nombre: "+nombreNegocio+" no fue encontrado."));

        return negocioEntidad.getId();
    }

    //GET listado de negocios x nombre parecido
    public List<NegocioEntidad> obtenerListadoDeNegociosConNombreAproximado(String nombreNegocio){

        return negocioRepositorio.getNegocioEntidadsByNombreLikeIgnoreCase(nombreNegocio);
    }

    // PUT negocio

    public Negocio actualizarNegocioPorID(Long id, NegocioRequest negocioRequest) throws EntityNotFoundException{

        NegocioEntidad negocioEntidad = negocioRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Negocio con id: "+ id+" no encontrado."));

        //Actualizo credenciales
        negocioEntidad.getCredencial().setTelefono(negocioRequest.getCredencial().getTelefono());
        negocioEntidad.getCredencial().setEmail(negocioRequest.getCredencial().getEmail());

        //Actualizo negocio
        negocioEntidad.setNombre(negocioRequest.getNombre());
        negocioEntidad.setAltura(negocioRequest.getAltura());
        negocioEntidad.setCalle(negocioRequest.getCalle());
        negocioEntidad.setDetalle(negocioRequest.getDetalle());
        negocioEntidad.setRubro(negocioRequest.getRubro());

        negocioEntidad = negocioRepositorio.save(negocioEntidad);
        return negocioMapper.toModel(negocioEntidad);

    }
    //Agregar metodo de pago
    public Negocio agregarMetodoPago(Long negocioId, Long metodoDePagoId) {
        NegocioEntidad negocio = negocioRepositorio.findById(negocioId)
                .orElseThrow(() -> new EntityNotFoundException("Negocio con id: "+ negocioId+" no encontrado."));

        MetodoDePagoEntidad metodo = metodosDePagoRepositorio.findById(metodoDePagoId)
                .orElseThrow(() -> new EntityNotFoundException("Metodo de pago con id: "+ metodoDePagoId+" no encontrado."));

        // Evito que ya este duplicado
        if (!negocio.getMetodosDePago().contains(metodo)) {
            negocio.getMetodosDePago().add(metodo);
            negocioRepositorio.save(negocio);
        }
        return negocioMapper.toModel(negocio);
    }
    //Eliminar metodo de pago
    public Negocio eliminarMetodoPago(Long negocioId, Long metodoDePagoId) {
        NegocioEntidad negocio = negocioRepositorio.findById(negocioId)
                .orElseThrow(() -> new EntityNotFoundException("Negocio con id: "+ negocioId+" no encontrado."));

        MetodoDePagoEntidad metodo = metodosDePagoRepositorio.findById(metodoDePagoId)
                .orElseThrow(() -> new EntityNotFoundException("Metodo de pago con id: "+ metodoDePagoId+" no encontrado."));

        negocio.getMetodosDePago().remove(metodo);

        return negocioMapper.toModel(negocioRepositorio.save(negocio));
    }

}

package com.miTurno.backend.servicio;


import com.miTurno.backend.entidad.*;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.TurnoMapper;
import com.miTurno.backend.model.Turno;
import com.miTurno.backend.repositorio.*;
import com.miTurno.backend.request.TurnoRequest;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {
    private final TurnoRepositorio turnoRepositorio;
    private final TurnoMapper turnoMapper;

    private final ClienteRepositorio clienteRepositorio;
    private final ProfesionalRepositorio profesionalRepositorio;
    private final NegocioRepositorio negocioRepositorio;
    private final HorarioProfesionalRepositorio horarioProfesionalRepositorio;
    private final ServicioRepositorio servicioRepositorio;
    private final MetodosDePagoRepositorio metodosDePagoRepositorio;
    private final EstadoTurnoRepositorio estadoTurnoRepositorio;

    @Autowired
    public TurnoService(TurnoRepositorio turnoRepositorio, TurnoMapper turnoMapper, ClienteRepositorio clienteRepositorio, ProfesionalRepositorio profesionalRepositorio, NegocioRepositorio negocioRepositorio, HorarioProfesionalRepositorio horarioProfesionalRepositorio, ServicioRepositorio servicioRepositorio, MetodosDePagoRepositorio metodosDePagoRepositorio, EstadoTurnoRepositorio estadoTurnoRepositorio) {
        this.turnoRepositorio = turnoRepositorio;
        this.turnoMapper = turnoMapper;
        this.clienteRepositorio = clienteRepositorio;
        this.profesionalRepositorio = profesionalRepositorio;
        this.negocioRepositorio = negocioRepositorio;
        this.horarioProfesionalRepositorio = horarioProfesionalRepositorio;
        this.servicioRepositorio = servicioRepositorio;
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
        this.estadoTurnoRepositorio = estadoTurnoRepositorio;
    }


    //GET
    public List<Turno> obtenerTodosLosTurnosPorNegocio(Long idNegocio) {


        return turnoMapper.toModelList(turnoRepositorio.findAllByNegocioEntidadId(idNegocio));
    }

    public Turno crearUnTurno(TurnoRequest nuevoTurno, Long idNegocio) {
        TurnoEntidad turnoEntidad = new TurnoEntidad();


        //todo si el turno se efectua, se lo tengo que agregar al cliente a su lista para el historial
        //busco si existe el cliente
        ClienteEntidad nuevoCliente = clienteRepositorio.findById(nuevoTurno.getIdCliente()).orElseThrow(() -> new UsuarioNoExistenteException(nuevoTurno.getIdCliente()));
        System.out.println("CLIENTE");//esta bien
        System.out.println(nuevoCliente);
        turnoEntidad.setClienteEntidad(nuevoCliente);

        //busco si existe el servicio
        ServicioEntidad nuevoServicio = servicioRepositorio.findById(nuevoTurno.getIdServicio()).orElseThrow(() -> new ServicioNoExisteException(nuevoTurno.getIdServicio()));
        turnoEntidad.setIdServicio(nuevoServicio);
        System.out.println("SERVICIO");//esta bien
        System.out.println(nuevoServicio);
        //busco si existe el profesional

        ProfesionalEntidad nuevoProfesional = profesionalRepositorio.findById(nuevoTurno.getIdProfesional()).orElseThrow(() -> new UsuarioNoExistenteException(nuevoTurno.getIdProfesional()));
        turnoEntidad.setProfesionalEntidad(nuevoProfesional);
        System.out.println("PROFESIONAL");//devuelve null
        System.out.println(nuevoProfesional);

        //busco si existe el negocio
        NegocioEntidad nuevoNegocio = negocioRepositorio.findById(idNegocio).orElseThrow(() -> new UsuarioNoExistenteException(idNegocio));
        turnoEntidad.setNegocioEntidad(nuevoNegocio);
        System.out.println("NEGOCIO");
        System.out.println(nuevoNegocio);

        //busco el horario profesional entidad
        HorarioProfesionalEntidad nuevoHorario = horarioProfesionalRepositorio.findById(nuevoTurno.getHorarioProfesional().getIdHorario()).orElseThrow(() -> new RecursoNoExisteException("horario"));
        turnoEntidad.setHorarioProfesionalEntidad(nuevoHorario);
        System.out.println("HORARIO");//devuelve null
        System.out.println(nuevoHorario);

        //busco el metodo de pago
        MetodoDePagoEntidad nuevoMetodoDePago = metodosDePagoRepositorio.findByMetodoDePago(nuevoTurno.getMetodosDePagoEnum());
        turnoEntidad.setMetodoDePagoEntidad(nuevoMetodoDePago);
        System.out.println("PAGO");
        System.out.println(nuevoMetodoDePago);

        //setteo los demas dato
        turnoEntidad.setFechaInicio(nuevoTurno.getFechaInicio());
        System.out.println("FECHA");
        System.out.println(nuevoTurno.getFechaInicio());

        EstadoTurnoEntidad estadoTurnoEntidad = estadoTurnoRepositorio.findByEstadoTurno(EstadoTurnoEnum.RESERVADO);
        turnoEntidad.setEstadoTurno(estadoTurnoEntidad);

        turnoEntidad = turnoRepositorio.save(turnoEntidad);

        nuevoProfesional.getTurnosAgendados().add(turnoEntidad);
        profesionalRepositorio.save(nuevoProfesional);

        nuevoCliente.getListadoDeTurnos().add(turnoEntidad);
        clienteRepositorio.save(nuevoCliente);


        return turnoMapper.toModel(turnoEntidad);
    }


    public Boolean eliminarTurnoPorId(Long idNegocio, Long id) {
        Boolean rta = false;
        if (turnoRepositorio.existsById(id)) {
            TurnoEntidad turnoEntidad = turnoRepositorio.findByNegocioEntidadIdAndId(idNegocio, id);
            EstadoTurnoEntidad estadoTurnoEntidad = estadoTurnoRepositorio.findByEstadoTurno(EstadoTurnoEnum.CANCELADO);
            turnoEntidad.setEstadoTurno(estadoTurnoEntidad);
            turnoRepositorio.save(turnoEntidad);
            rta = true;
        }

        return rta;
    }

    public Turno actualizarTurnoPorId(Long idNegocio, Long idTurno, TurnoRequest turnoRequest) {
        TurnoEntidad turnoEntidad = turnoRepositorio.findById(idTurno)
                .orElseThrow(() -> new RecursoNoExisteException("Turno no encontrado"));

        // Verificar si el negocio es correcto
        if (!turnoEntidad.getNegocioEntidad().getId().equals(idNegocio)) {
            throw new UsuarioNoExistenteException(idNegocio);
        }

        // Actualizar los campos del turno
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(turnoRequest.getIdCliente())
                .orElseThrow(() -> new UsuarioNoExistenteException(turnoRequest.getIdCliente()));
        turnoEntidad.setClienteEntidad(clienteEntidad);

        ServicioEntidad servicioEntidad = servicioRepositorio.findById(turnoRequest.getIdServicio())
                .orElseThrow(() -> new ServicioNoExisteException(turnoRequest.getIdServicio()));
        turnoEntidad.setIdServicio(servicioEntidad);

        ProfesionalEntidad profesionalEntidad = profesionalRepositorio.findById(turnoRequest.getIdProfesional())
                .orElseThrow(() -> new UsuarioNoExistenteException(turnoRequest.getIdProfesional()));
        turnoEntidad.setProfesionalEntidad(profesionalEntidad);

        HorarioProfesionalEntidad horarioProfesionalEntidad = horarioProfesionalRepositorio.findById(turnoRequest.getHorarioProfesional().getIdHorario())
                .orElseThrow(() -> new RecursoNoExisteException("Horario no encontrado"));
        turnoEntidad.setHorarioProfesionalEntidad(horarioProfesionalEntidad);

        MetodoDePagoEntidad metodoDePagoEntidad = metodosDePagoRepositorio.findByMetodoDePago(turnoRequest.getMetodosDePagoEnum());
        turnoEntidad.setMetodoDePagoEntidad(metodoDePagoEntidad);

        turnoEntidad.setFechaInicio(turnoRequest.getFechaInicio());

        EstadoTurnoEntidad estadoTurnoEntidad = estadoTurnoRepositorio.findByEstadoTurno(turnoRequest.getEstadoTurno());
        turnoEntidad.setEstadoTurno(estadoTurnoEntidad);

        // Guardar los cambios
        turnoRepositorio.save(turnoEntidad);

        return turnoMapper.toModel(turnoEntidad);
    }
}
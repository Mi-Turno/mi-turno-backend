package com.miTurno.backend.servicio;
import com.miTurno.backend.data.domain.*;
import com.miTurno.backend.data.repositorio.*;
import com.miTurno.backend.data.mapper.TurnoMapper;
import com.miTurno.backend.data.dtos.model.Turno;
import com.miTurno.backend.data.dtos.request.TurnoRequest;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
import jakarta.persistence.EntityNotFoundException;
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

    public Turno crearUnTurno(
            TurnoRequest nuevoTurno,
            Long idNegocio) throws EntityNotFoundException{
        TurnoEntidad turnoEntidad = new TurnoEntidad();


        //todo si el turno se efectua, se lo tengo que agregar al cliente a su lista para el historial
        //busco si existe el cliente
        ClienteEntidad nuevoCliente = clienteRepositorio.findById(nuevoTurno.getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id: "+nuevoTurno.getIdCliente() +" no encontrado."));

        System.out.println("CLIENTE");//esta bien
        System.out.println(nuevoCliente);
        turnoEntidad.setClienteEntidad(nuevoCliente);

        //busco si existe el servicio
        ServicioEntidad nuevoServicio = servicioRepositorio.findById(nuevoTurno.getIdServicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio con id: "+nuevoTurno.getIdServicio() +" no encontrado."));
        turnoEntidad.setIdServicio(nuevoServicio);
        System.out.println("SERVICIO");//esta bien
        System.out.println(nuevoServicio);
        //busco si existe el profesional

        ProfesionalEntidad nuevoProfesional = profesionalRepositorio.findById(nuevoTurno.getIdProfesional())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id: "+nuevoTurno.getIdProfesional() +" no encontrado."));
        turnoEntidad.setProfesionalEntidad(nuevoProfesional);
        System.out.println("PROFESIONAL");//devuelve null
        System.out.println(nuevoProfesional);

        //busco si existe el negocio
        NegocioEntidad nuevoNegocio = negocioRepositorio.findById(idNegocio)
                .orElseThrow(() -> new EntityNotFoundException("Negocio con id: "+ idNegocio +" no encontrado."));
        turnoEntidad.setNegocioEntidad(nuevoNegocio);
        System.out.println("NEGOCIO");
        System.out.println(nuevoNegocio);

        //busco el horario profesional entidad
        HorarioProfesionalEntidad nuevoHorario = horarioProfesionalRepositorio.findById(nuevoTurno.getHorarioProfesional().getIdHorario())
                .orElseThrow(() -> new EntityNotFoundException("Horario con id:"+ nuevoTurno.getHorarioProfesional().getIdHorario() +" no encontrado."));
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

    public Turno modificarTurnoPorId(Long idNegocio, Long id, EstadoTurnoEnum estadoTurnoEnum) {
        TurnoEntidad turnoEntidad = turnoRepositorio.findByNegocioEntidadIdAndId(idNegocio, id);
        EstadoTurnoEntidad estadoTurnoEntidad = estadoTurnoRepositorio.findByEstadoTurno(estadoTurnoEnum);
        turnoEntidad.setEstadoTurno(estadoTurnoEntidad);
        return turnoMapper.toModel(turnoRepositorio.save(turnoEntidad));
    }

}
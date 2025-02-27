package com.miTurno.backend.servicio;

import com.miTurno.backend.data.domain.*;
import com.miTurno.backend.data.repositorio.*;
import com.miTurno.backend.data.mapper.TurnoMapper;
import com.miTurno.backend.data.dtos.response.Turno;
import com.miTurno.backend.data.dtos.request.TurnoRequest;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            Long idNegocio) throws EntityNotFoundException {
        TurnoEntidad turnoEntidad = new TurnoEntidad();


        //todo si el turno se efectua, se lo tengo que agregar al cliente a su lista para el historial
        //busco si existe el cliente
        ClienteEntidad nuevoCliente = clienteRepositorio.findById(nuevoTurno.getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id: " + nuevoTurno.getIdCliente() + " no encontrado."));

        System.out.println("CLIENTE");//esta bien
        System.out.println(nuevoCliente);
        turnoEntidad.setClienteEntidad(nuevoCliente);

        //busco si existe el servicio
        ServicioEntidad nuevoServicio = servicioRepositorio.findById(nuevoTurno.getIdServicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio con id: " + nuevoTurno.getIdServicio() + " no encontrado."));
        turnoEntidad.setIdServicio(nuevoServicio);
        System.out.println("SERVICIO");//esta bien
        System.out.println(nuevoServicio);
        //busco si existe el profesional

        ProfesionalEntidad nuevoProfesional = profesionalRepositorio.findById(nuevoTurno.getIdProfesional())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id: " + nuevoTurno.getIdProfesional() + " no encontrado."));
        turnoEntidad.setProfesionalEntidad(nuevoProfesional);
        System.out.println("PROFESIONAL");//devuelve null
        System.out.println(nuevoProfesional);

        //busco si existe el negocio
        NegocioEntidad nuevoNegocio = negocioRepositorio.findById(idNegocio)
                .orElseThrow(() -> new EntityNotFoundException("Negocio con id: " + idNegocio + " no encontrado."));
        turnoEntidad.setNegocioEntidad(nuevoNegocio);
        System.out.println("NEGOCIO");
        System.out.println(nuevoNegocio);

        //busco el horario profesional entidad
        HorarioProfesionalEntidad nuevoHorario = horarioProfesionalRepositorio.findById(nuevoTurno.getHorarioProfesional().getIdHorario())
                .orElseThrow(() -> new EntityNotFoundException("Horario con id:" + nuevoTurno.getHorarioProfesional().getIdHorario() + " no encontrado."));
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

    public Turno obtenerTurnoPorId(Long idTurno) {
        return turnoMapper.toModel(turnoRepositorio.findById(idTurno)
                .orElseThrow(() -> new EntityNotFoundException("Turno con id: " + idTurno + " no encontrado.")));
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

    /**
     * Este metodo lo que hace es revisar en un periodo de 10 minutos hacia atras (Por si ya pasamos la hora cuando lo revisa) que el turno este en curso por si el front esta cerrado o sin conexion
     */
    @Async
    @Transactional
    @Scheduled(fixedRate = 180000) //evalua cada 3 minutos
    protected void ponerEnCursoLosTurnos() {
        // Obtengo los turnos reservados
        List<TurnoEntidad> turnoEntidadList = estadoTurnoRepositorio.findByEstadoTurno(EstadoTurnoEnum.RESERVADO).getTurnoEntidad();

        if (!turnoEntidadList.isEmpty()) {
            // Obtengo la fecha y hora actual con una tolerancia de 10 minutos hacia atras por ej 12:00 analiza hasta 11:50
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime tolerancia = ahora.minusMinutes(10);

            for (TurnoEntidad turnoEntidad : turnoEntidadList) {
                // Genero el formato de fecha para comparar
                LocalDate fechaTurno = turnoEntidad.getFechaInicio();
                LocalTime horaTurno = turnoEntidad.getHorarioProfesionalEntidad().getHoraInicio();
                LocalDateTime fechaHoraTurno = LocalDateTime.of(fechaTurno, horaTurno);

                // Verifico que la fecha y hora del turno se encuentre entre (ahora - 10 min) y ahora
                if (!fechaHoraTurno.isBefore(tolerancia) && !fechaHoraTurno.isAfter(ahora)) {
                    modificarEstadoTurno(turnoEntidad,EstadoTurnoEnum.EN_CURSO);
                }
            }
        } else {
            System.out.println("No hay turnos reservados.");
        }
    }

    private void modificarEstadoTurno(TurnoEntidad turnoEntidad, EstadoTurnoEnum estadoTurnoEnum) {
        EstadoTurnoEntidad estadoEnCurso = estadoTurnoRepositorio.findByEstadoTurno(estadoTurnoEnum);
        turnoEntidad.setEstadoTurno(estadoEnCurso);
        // Se guarda el turno actualizado en la base de datos
        turnoRepositorio.save(turnoEntidad);
    }

}
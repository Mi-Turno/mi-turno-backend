package com.miTurno.backend.servicio;


import com.miTurno.backend.entidad.*;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.TurnoMapper;
import com.miTurno.backend.DTO.Turno;
import com.miTurno.backend.repositorio.*;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Autowired
    public TurnoService(TurnoRepositorio turnoRepositorio, TurnoMapper turnoMapper, ClienteRepositorio clienteRepositorio,ProfesionalRepositorio profesionalRepositorio,NegocioRepositorio negocioRepositorio,HorarioProfesionalRepositorio horarioProfesionalRepositorio,ServicioRepositorio servicioRepositorio,MetodosDePagoRepositorio metodosDePagoRepositorio) {
        this.turnoRepositorio = turnoRepositorio;
        this.turnoMapper = turnoMapper;
        this.clienteRepositorio =clienteRepositorio;
        this.profesionalRepositorio = profesionalRepositorio;
        this.negocioRepositorio = negocioRepositorio;
        this.horarioProfesionalRepositorio = horarioProfesionalRepositorio;
        this.servicioRepositorio = servicioRepositorio;
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
    }
    //GET
   /* public List<TurnoEntidad> obtenerTodosLosTurnosPorNegocio(Long idNegocio){
        return turnoRepositorio.findByNegocioEntidad_Id(idNegocio);
    }
*/
    //todo agregar comportamiento para esto en el repo
    /*public List<Turno> obtenerListadoPorProfesional(Long idProfesional){
        return turnoRepositorio.findAll().stream().map(turnoMapper::toModel).toList();
    }*/
    //POST

    public Turno crearUnTurno(Turno nuevoTurno){
        TurnoEntidad turnoEntidad = new TurnoEntidad();
        System.out.println(nuevoTurno);
        //todo si el turno se efectua, se lo tengo que agregar al cliente a su lista para el historial
        //busco si existe el cliente
        ClienteEntidad nuevoCliente = clienteRepositorio.findById(nuevoTurno.getIdCliente()).orElseThrow(()->new UsuarioNoExistenteException(nuevoTurno.getIdCliente()));
        System.out.println("CLIENTE");//esta bien
        System.out.println(nuevoCliente);
        turnoEntidad.setClienteEntidad(nuevoCliente);

        //busco si existe el servicio
        ServicioEntidad nuevoServicio = servicioRepositorio.findById(nuevoTurno.getIdServicio()).orElseThrow(()->new ServicioNoExisteException(nuevoTurno.getIdServicio()));
        turnoEntidad.setIdServicio(nuevoServicio);
        System.out.println("SERVICIO");//esta bien
        System.out.println(nuevoServicio);
        //busco si existe el profesional

        ProfesionalEntidad nuevoProfesional = profesionalRepositorio.findById(nuevoTurno.getIdProfesional()).orElseThrow(()->new UsuarioNoExistenteException(nuevoTurno.getIdProfesional()));
        turnoEntidad.setProfesionalEntidad(nuevoProfesional);
        System.out.println("PROFESIONAL");//devuelve null
        System.out.println(nuevoProfesional);

        //busco si existe el negocio
        NegocioEntidad nuevoNegocio = negocioRepositorio.findById(nuevoTurno.getIdNegocio()).orElseThrow(()-> new UsuarioNoExistenteException(nuevoTurno.getIdNegocio()));
        turnoEntidad.setNegocioEntidad(nuevoNegocio);
        System.out.println("NEGOCIO");
        System.out.println(nuevoNegocio);

        //busco el horario profesional entidad
        HorarioProfesionalEntidad nuevoHorario = horarioProfesionalRepositorio.findById(nuevoTurno.getHorarioProfesional().getIdHorario()).orElseThrow(()->new RecursoNoExisteException("horario"));
        turnoEntidad.setHorarioProfesionalEntidad(nuevoHorario);
        System.out.println("HORARIO");//devuelve null
        System.out.println(nuevoHorario);

        //busco el metodo de pago
        MetodoDePagoEntidad nuevoMetodoDePago = metodosDePagoRepositorio.findBymetodosDePago(nuevoTurno.getMetodosDePagoEnum());
        turnoEntidad.setMetodoDePagoEntidad(nuevoMetodoDePago);
        System.out.println("PAGO");
        System.out.println(nuevoMetodoDePago);

        //setteo los demas dato
        turnoEntidad.setFechaInicio(nuevoTurno.getFechaInicio());
        System.out.println("FECHA");
        System.out.println(nuevoTurno.getFechaInicio());
        turnoEntidad.setEstado(true);

        turnoEntidad = turnoRepositorio.save(turnoEntidad);

        nuevoProfesional.getTurnosAgendados().add(turnoEntidad);

        profesionalRepositorio.save(nuevoProfesional);

        return turnoMapper.toModel(turnoEntidad);
    }
    /*public Boolean eliminarTurnoPorId(Long idNegocio,Long id){
        Boolean rta = false;
        if(turnoRepositorio.existsById(id)){
            TurnoEntidad turnoEntidad = turnoRepositorio.findByNegocioEntidad_IdAndTurnoId(idNegocio, id);
            turnoEntidad.setEstado(false);
            turnoRepositorio.save(turnoEntidad);
            rta=true;
        }

        return rta;
    }
*/

}

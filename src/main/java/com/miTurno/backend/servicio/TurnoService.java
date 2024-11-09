package com.miTurno.backend.servicio;


import com.miTurno.backend.entidad.*;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.excepcion.TurnoNoExistenteException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.TurnoMapper;
import com.miTurno.backend.DTO.Turno;
import com.miTurno.backend.repositorio.*;
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

        //busco si existe el cliente
        ClienteEntidad nuevoCliente = clienteRepositorio.findById(nuevoTurno.getIdCliente()).orElseThrow(()->new UsuarioNoExistenteException(nuevoTurno.getIdCliente()));
        turnoEntidad.setClienteEntidad(nuevoCliente);

        //busco si existe el servicio
        ServicioEntidad nuevoServicio = servicioRepositorio.findById(nuevoTurno.getIdServicio()).orElseThrow(()->new ServicioNoExisteException(nuevoTurno.getIdServicio()));
        turnoEntidad.setIdServicio(nuevoServicio);

        //busco si existe el profesional
        ProfesionalEntidad nuevoProfesional = profesionalRepositorio.findById(nuevoTurno.getHorarioProfesional().getIdProfesional()).orElseThrow(()->new UsuarioNoExistenteException(nuevoTurno.getHorarioProfesional().getIdProfesional()));
        turnoEntidad.setProfesionalEntidad(nuevoProfesional);

        //busco si existe el negocio
        NegocioEntidad nuevoNegocio = negocioRepositorio.findById(nuevoTurno.getIdNegocio()).orElseThrow(()-> new UsuarioNoExistenteException(nuevoTurno.getIdNegocio()));
        turnoEntidad.setNegocioEntidad(nuevoNegocio);

        //busco el horario profesional entidad
        HorarioProfesionalEntidad nuevoHorario = horarioProfesionalRepositorio.findById(nuevoTurno.getHorarioProfesional().getIdHorario()).orElseThrow(()->new RecursoNoExisteException("horario"));
        turnoEntidad.setHorarioProfesionalEntidad(nuevoHorario);


        //busco el metodo de pago
        MetodoDePagoEntidad nuevoMetodoDePago = metodosDePagoRepositorio.findBymetodosDePago(nuevoTurno.getMetodosDePagoEnum());
        turnoEntidad.setMetodoDePagoEntidad(nuevoMetodoDePago);

        //setteo los demas datos
        turnoEntidad.setFechaInicio(nuevoTurno.getFechaInicio());
        turnoEntidad.setEstado(true);


        turnoEntidad = turnoRepositorio.save(turnoEntidad);
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

package com.miTurno.backend.servicio;


import com.miTurno.backend.entidad.TurnoEntidad;
import com.miTurno.backend.excepcion.TurnoNoExistenteException;
import com.miTurno.backend.mapper.TurnoMapper;
import com.miTurno.backend.DTO.Turno;
import com.miTurno.backend.repositorio.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {
    private final TurnoRepositorio turnoRepositorio;
    private final TurnoMapper turnoMapper;

    @Autowired
    public TurnoService(TurnoRepositorio turnoRepositorio, TurnoMapper turnoMapper) {
        this.turnoRepositorio = turnoRepositorio;
        this.turnoMapper = turnoMapper;
    }
    //GET
    public List<TurnoEntidad> obtenerTodosLosTurnos(){
        return turnoRepositorio.fin
    }

    //todo agregar comportamiento para esto en el repo
    /*public List<Turno> obtenerListadoPorProfesional(Long idProfesional){
        return turnoRepositorio.findAll().stream().map(turnoMapper::toModel).toList();
    }*/
    //POST

    public Turno crearUnTurno(Turno nuevoTurno){
        TurnoEntidad turnoEntidad = new TurnoEntidad();

        turnoEntidad.setProfesionalEntidad(nuevoTurno.getIdProfesional());
        turnoEntidad.setClienteEntidad(nuevoTurno.getIdCliente());
        turnoEntidad.setNegocioEntidad(nuevoTurno.getIdNegocio());
        turnoEntidad.setFechaInicio(nuevoTurno.getFechaInicio());
        turnoEntidad.setHorario(nuevoTurno.getHorario());
        turnoEntidad.setEstado(true);
        turnoEntidad.setMetodoDePagoEntidad(nuevoTurno.getMetodosDePagoEnum());
        turnoEntidad = turnoRepositorio.save(turnoEntidad);
        return turnoMapper.toModel(turnoEntidad);
    }
    public Boolean eliminarTurnoPorId(Long id){
        Boolean rta = false;
        if(turnoRepositorio.existsById(id)){
            TurnoEntidad turnoEntidad = turnoRepositorio.findById(id).orElseThrow(()-> new TurnoNoExistenteException(id));
            turnoEntidad.setEstado(false);
            turnoRepositorio.save(turnoEntidad);
            rta=true;
        }

        return rta;
    }


}

package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.DiaEntidad;
import com.miTurno.backend.entidad.HorarioXProfesionalEntidad;
import com.miTurno.backend.mapper.HorarioXProfesionalMapper;
import com.miTurno.backend.modelo.HorarioXProfesional;
import com.miTurno.backend.repositorio.DiaRepositorio;
import com.miTurno.backend.repositorio.HorarioXProfesionalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioXProfesionalService {
    //atributos
    private final HorarioXProfesionalRepositorio horarioXProfesionalRepositorio;
    private final DiaRepositorio diaRepositorio;
    private final HorarioXProfesionalMapper horarioXProfesionalMapper;

    //constructores
    @Autowired
    public HorarioXProfesionalService(HorarioXProfesionalRepositorio horarioXProfesionalRepositorio, HorarioXProfesionalMapper horarioXProfesionalMapper,DiaRepositorio diaRepositorio) {
        this.horarioXProfesionalRepositorio = horarioXProfesionalRepositorio;
        this.horarioXProfesionalMapper = horarioXProfesionalMapper;
        this.diaRepositorio = diaRepositorio;
    }

    public HorarioXProfesional crearUnHorarioXProfesional(HorarioXProfesional nuevoHXP){
        HorarioXProfesionalEntidad horarioXProfesionalEntidad = new HorarioXProfesionalEntidad();
        horarioXProfesionalEntidad.setIdHorarioXProfesional(nuevoHXP.getIdHorarioXProfesional());
        horarioXProfesionalEntidad.setIdProfesional(nuevoHXP.getIdProfesional());
        DiaEntidad nuevoDiaEntidad = diaRepositorio.findByDia(nuevoHXP.getDia());
        horarioXProfesionalEntidad.setDiaEntidad(nuevoDiaEntidad);
        horarioXProfesionalEntidad.setHorario(nuevoHXP.getHorario());
        horarioXProfesionalEntidad = horarioXProfesionalRepositorio.save(horarioXProfesionalEntidad);
        return horarioXProfesionalMapper.toModel(horarioXProfesionalEntidad);

    }

}

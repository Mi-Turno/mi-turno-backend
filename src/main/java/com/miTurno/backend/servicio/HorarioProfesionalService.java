package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.HorarioProfesional;
import com.miTurno.backend.entidad.DiaEntidad;
import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.pivotEntidad.HorarioXProfesionalEntidad;
import com.miTurno.backend.mapper.HorarioProfesionalMapper;
import com.miTurno.backend.repositorio.DiaRepositorio;
import com.miTurno.backend.repositorio.HorarioProfesionalRepositorio;
import com.miTurno.backend.repositorio.pivotRepositorios.HorarioXProfesionalRepositorio;
import com.miTurno.backend.tipos.DiasEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioProfesionalService {
    //atributos
    private final HorarioProfesionalRepositorio horarioProfesionalRepositorio;
    private final DiaRepositorio diaRepositorio;
    private final HorarioProfesionalMapper horarioProfesionalMapper;

    //constructores
    @Autowired
    public HorarioProfesionalService(HorarioProfesionalRepositorio horarioProfesionalRepositorio, HorarioProfesionalMapper horarioProfesionalMapper, DiaRepositorio diaRepositorio) {
        this.horarioProfesionalRepositorio = horarioProfesionalRepositorio;
        this.horarioProfesionalMapper = horarioProfesionalMapper;
        this.diaRepositorio = diaRepositorio;
    }

    public HorarioProfesional crearUnHorarioXProfesional(HorarioProfesional nuevoHP){
        HorarioProfesionalEntidad horarioProfesionalEntidad = new HorarioProfesionalEntidad();

        //todo obtener el profesiona por id

        ProfesionalEntidad profesionalEntidad =

        horarioProfesionalEntidad.setProfesionalEntidad(nuevoHP.getIdProfesional());
        DiaEntidad nuevoDiaEntidad = diaRepositorio.findByDia(nuevoHXP.getDia());
        horarioProfesionalEntidad.setDiaEntidad(nuevoDiaEntidad);
        horarioProfesionalEntidad.setHorario(nuevoHXP.getHorario());
        horarioProfesionalEntidad = horarioProfesionalRepositorio.save(horarioXProfesionalEntidad);
        return horarioProfesionalMapper.toModel(horarioXProfesionalEntidad);

    }


    public List<HorarioXProfesionalEntidad> obtenerHorariosPorProfesionalYDia(Long idProfesional, DiasEnum dia) {
        return horarioXProfesionalRepositorio.findByIdProfesionalAndDiaEntidad_Dia(idProfesional, dia);
    }


    public boolean eliminarHorarioPorProfesional(Long idHorarioPorProfesional) {
        boolean rta = false;
        if(horarioXProfesionalRepositorio.existsById(idHorarioPorProfesional)) {
            horarioXProfesionalRepositorio.deleteById(idHorarioPorProfesional);
            rta = true;
        }
        return rta;
    }

}

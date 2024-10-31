package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.HorarioProfesional;
import com.miTurno.backend.entidad.DiaEntidad;
import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;

import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.HorarioProfesionalMapper;
import com.miTurno.backend.repositorio.DiaRepositorio;
import com.miTurno.backend.repositorio.HorarioProfesionalRepositorio;

import com.miTurno.backend.repositorio.ProfesionalRepositorio;
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
    private final ProfesionalRepositorio profesionalRepositorio;

    //constructores
    @Autowired
    public HorarioProfesionalService(HorarioProfesionalRepositorio horarioProfesionalRepositorio, HorarioProfesionalMapper horarioProfesionalMapper, DiaRepositorio diaRepositorio,ProfesionalRepositorio profesionalRepositorio) {
        this.horarioProfesionalRepositorio = horarioProfesionalRepositorio;
        this.horarioProfesionalMapper = horarioProfesionalMapper;
        this.diaRepositorio = diaRepositorio;
        this.profesionalRepositorio = profesionalRepositorio;
    }

    public HorarioProfesional crearUnHorarioXProfesional(HorarioProfesional nuevoHP){
        HorarioProfesionalEntidad horarioProfesionalEntidad = new HorarioProfesionalEntidad();

        //todo obtener el profesiona por id

        ProfesionalEntidad profesionalEntidad = profesionalRepositorio.findById(nuevoHP.getIdProfesional()).orElseThrow(()-> new UsuarioNoExistenteException(nuevoHP.getIdProfesional()));
        horarioProfesionalEntidad.setProfesionalEntidad(profesionalEntidad);

        DiaEntidad nuevoDiaEntidad = diaRepositorio.findByDia(nuevoHP.getDia());
        horarioProfesionalEntidad.setDiaEntidad(nuevoDiaEntidad);
        horarioProfesionalEntidad.setIdHorario(nuevoHP.getIdHorario());
        horarioProfesionalEntidad = horarioProfesionalRepositorio.save(horarioProfesionalEntidad);
        return horarioProfesionalMapper.toModel(horarioProfesionalEntidad);

    }


    /*public List<HorarioProfesional> obtenerHorariosPorProfesionalYDia(Long idProfesional, DiasEnum dia) {

        List<HorarioProfesionalEntidad> horarioProfesionalEntidadList = horarioProfesionalRepositorio.findByIdProfesionalAndDiaEntidad_Dia(idProfesional, dia);

        return horarioProfesionalEntidadList.stream().map(horarioProfesionalMapper::toModel).toList();
    }


    public boolean eliminarHorarioPorProfesional(Long idHorarioPorProfesional) {
        boolean rta = false;
        if(horarioXProfesionalRepositorio.existsById(idHorarioPorProfesional)) {
            horarioXProfesionalRepositorio.deleteById(idHorarioPorProfesional);
            rta = true;
        }
        return rta;
    }*/

}

package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.HorarioProfesional;
import com.miTurno.backend.entidad.DiaEntidad;
import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;

import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.HorarioProfesionalMapper;
import com.miTurno.backend.repositorio.DiaRepositorio;
import com.miTurno.backend.repositorio.HorarioProfesionalRepositorio;

import com.miTurno.backend.repositorio.NegocioRepositorio;
import com.miTurno.backend.repositorio.ProfesionalRepositorio;
import com.miTurno.backend.request.HorarioProfesionalRequest;
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
    private final NegocioRepositorio negocioRepositorio;

    //constructores
    @Autowired
    public HorarioProfesionalService(HorarioProfesionalRepositorio horarioProfesionalRepositorio, HorarioProfesionalMapper horarioProfesionalMapper, DiaRepositorio diaRepositorio, ProfesionalRepositorio profesionalRepositorio, NegocioRepositorio negocioRepositorio) {
        this.horarioProfesionalRepositorio = horarioProfesionalRepositorio;
        this.horarioProfesionalMapper = horarioProfesionalMapper;
        this.diaRepositorio = diaRepositorio;
        this.profesionalRepositorio = profesionalRepositorio;
        this.negocioRepositorio = negocioRepositorio;
    }

    public HorarioProfesionalEntidad crearUnHorarioXProfesional(Long idNegocio, Long idProfesional, HorarioProfesionalRequest nuevoHP){

        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio).orElseThrow(()-> new RecursoNoExisteException("Id negocio"));
        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findById(idProfesional).orElseThrow(()-> new RecursoNoExisteException("Id profesional"));
        DiaEntidad diaEntidad = diaRepositorio.findByDia(nuevoHP.getDia());

        HorarioProfesionalEntidad horarioProfesionalEntidad = HorarioProfesionalEntidad.builder()
                .horaInicio(nuevoHP.getHoraInicio())
                .diaEntidad(diaEntidad)
                .profesionalEntidad(profesionalEntidad)
                .estado(true)
                .build();

        return horarioProfesionalRepositorio.save(horarioProfesionalEntidad);
    }

    public List<HorarioProfesionalEntidad> obtenerListadoDeHorariosDeUnProfesional(Long idNegocio, Long idProfesional){
        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio).orElseThrow(()-> new RecursoNoExisteException("Id negocio"));
        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findById(idProfesional).orElseThrow(()-> new RecursoNoExisteException("Id profesional"));

        return horarioProfesionalRepositorio.findByProfesionalEntidad_IdUsuario(idProfesional);
    }

    public List<HorarioProfesionalEntidad> obtenerHorariosPorProfesionalYDia(Long idNegocio,Long idProfesional, Long idDia) {
        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio).orElseThrow(()-> new RecursoNoExisteException("Id negocio"));
        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findById(idProfesional).orElseThrow(()-> new RecursoNoExisteException("Id profesional"));


        //DiaEntidad diaEntidad = diaRepositorio.findById(idDia).orElseThrow(()-> new RecursoNoExisteException("Id dia"));
        DiasEnum diaEnum = DiasEnum.fromOrdinal(Math.toIntExact(idDia));

        return horarioProfesionalRepositorio.findByProfesionalEntidad_IdUsuarioAndDiaEntidad_Dia(idProfesional, diaEnum);
    }

    public HorarioProfesionalEntidad obtenerHorarioProfesionalPorId(Long idNegocio,Long idProfesional, Long idHorarioProfesional){
        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio).orElseThrow(()-> new RecursoNoExisteException("Id negocio"));
        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findById(idProfesional).orElseThrow(()-> new RecursoNoExisteException("Id profesional"));

        return  horarioProfesionalRepositorio.findById(idHorarioProfesional).orElseThrow(()->new RecursoNoExisteException("Id Horario Profesional"));

    }

//    public boolean eliminarHorarioPorProfesional(Long idHorarioPorProfesional) {
//        boolean rta = false;
//        if(horarioXProfesionalRepositorio.existsById(idHorarioPorProfesional)) {
//            horarioXProfesionalRepositorio.deleteById(idHorarioPorProfesional);
//            rta = true;
//        }
//        return rta;
//    }

}

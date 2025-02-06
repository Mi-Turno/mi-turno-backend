package com.miTurno.backend.servicio;
import com.miTurno.backend.data.dtos.response.HorarioProfesional;
import com.miTurno.backend.data.domain.DiaEntidad;
import com.miTurno.backend.data.domain.HorarioProfesionalEntidad;
import com.miTurno.backend.data.domain.NegocioEntidad;
import com.miTurno.backend.data.domain.ProfesionalEntidad;
import com.miTurno.backend.data.mapper.HorarioProfesionalMapper;
import com.miTurno.backend.data.repositorio.DiaRepositorio;
import com.miTurno.backend.data.repositorio.HorarioProfesionalRepositorio;
import com.miTurno.backend.data.repositorio.NegocioRepositorio;
import com.miTurno.backend.data.repositorio.ProfesionalRepositorio;
import com.miTurno.backend.data.dtos.request.HorarioProfesionalRequest;
import com.miTurno.backend.tipos.DiasEnum;
import jakarta.persistence.EntityNotFoundException;
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

    public HorarioProfesional crearUnHorarioXProfesional(Long idNegocio,
                                                         Long idProfesional,
                                                         HorarioProfesionalRequest nuevoHP) throws EntityNotFoundException {

        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio)
                .orElseThrow(()-> new EntityNotFoundException("Negocio con id:"+ idNegocio +" no encontrado."));

        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findById(idProfesional)
                .orElseThrow(()-> new EntityNotFoundException("Profesional con id:"+ idNegocio +" no encontrado."));

        DiaEntidad diaEntidad = diaRepositorio.findByDia(nuevoHP.getDia());

        HorarioProfesionalEntidad horarioProfesionalEntidad = HorarioProfesionalEntidad.builder()
                .horaInicio(nuevoHP.getHoraInicio())
                .diaEntidad(diaEntidad)
                .profesionalEntidad(profesionalEntidad)
                .estado(true)
                .build();



        return horarioProfesionalMapper.toModel(horarioProfesionalRepositorio.save(horarioProfesionalEntidad));
    }

    public List<HorarioProfesionalEntidad> obtenerListadoDeHorariosDeUnProfesional(Long idNegocio, Long idProfesional)throws EntityNotFoundException{
        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio)
                .orElseThrow(()-> new EntityNotFoundException("Negocio con id:"+ idNegocio +" no encontrado."));

        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findById(idProfesional)
                .orElseThrow(()-> new EntityNotFoundException("Profesional con id:"+ idNegocio +" no encontrado."));

        return horarioProfesionalRepositorio.findByProfesionalEntidadId(idProfesional);
    }

    public List<HorarioProfesionalEntidad> obtenerHorariosPorProfesionalYDia(Long idNegocio,Long idProfesional, Long idDia) throws EntityNotFoundException{
        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio)
                .orElseThrow(()-> new EntityNotFoundException("Negocio con id:"+ idNegocio +" no encontrado."));

        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findById(idProfesional)
                .orElseThrow(()-> new EntityNotFoundException("Profesional con id:"+ idNegocio +" no encontrado."));


        //DiaEntidad diaEntidad = diaRepositorio.findById(idDia).orElseThrow(()-> new RecursoNoExisteException("Id dia"));
        DiasEnum diaEnum = DiasEnum.fromOrdinal(Math.toIntExact(idDia));

        return horarioProfesionalRepositorio.findByProfesionalEntidadIdAndDiaEntidad_Dia(idProfesional, diaEnum);
    }

    public HorarioProfesionalEntidad obtenerHorarioProfesionalPorId(Long idNegocio,Long idProfesional, Long idHorarioProfesional) throws EntityNotFoundException{

        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio)
                .orElseThrow(()-> new EntityNotFoundException("Negocio con id:"+ idNegocio +" no encontrado."));

        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findById(idProfesional)
                .orElseThrow(()-> new EntityNotFoundException("Profesional con id:"+ idNegocio +" no encontrado."));

        return  horarioProfesionalRepositorio.findById(idHorarioProfesional)
                .orElseThrow(()->new EntityNotFoundException("Horario del profesional con id: "+ idHorarioProfesional+" no encontrado."));

    }

    public HorarioProfesional modificarEstadoHorario(Long id, boolean estado) {
        HorarioProfesionalEntidad horarioProfesionalEntidad = horarioProfesionalRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario con ID " + id + " no encontrado"));
        horarioProfesionalEntidad.setEstado(estado);

        return horarioProfesionalMapper.toModel(horarioProfesionalRepositorio.save(horarioProfesionalEntidad));
    }

    public boolean eliminarHorarioPorProfesional(Long idHorarioPorProfesional) {
        boolean rta = false;
        if(horarioProfesionalRepositorio.existsById(idHorarioPorProfesional)) {
            horarioProfesionalRepositorio.deleteById(idHorarioPorProfesional);
            rta = true;
        }
        return rta;
    }

}

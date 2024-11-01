package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.RolIncorrectoException;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.repositorio.NegocioRepositorio;
import com.miTurno.backend.repositorio.ProfesionalRepositorio;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.request.UsuarioRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesionalService {
    private final ProfesionalRepositorio profesionalRepositorio;
    private final RolRepositorio rolRepositorio;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final NegocioRepositorio negocioRepositorio;
    private final ProfesionalMapper profesionalMapper;
    private final UsuarioRepositorio usuarioRepositorio;

    public ProfesionalService(ProfesionalRepositorio profesionalRepositorio, RolRepositorio rolRepositorio, UsuarioService usuarioService, UsuarioMapper usuarioMapper, NegocioRepositorio negocioRepositorio, ProfesionalMapper profesionalMapper, UsuarioRepositorio usuarioRepositorio) {
        this.profesionalRepositorio = profesionalRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.profesionalMapper = profesionalMapper;
        this.usuarioRepositorio = usuarioRepositorio;
    }


    //POST profesional

    public ProfesionalEntidad crearUnprofesional(Long idNegocio, ProfesionalRequest profesionalRequest) throws RolIncorrectoException,RecursoNoExisteException {


        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(profesionalRequest.getIdRol()).get().getRol();

        if (rolUsuarioEnum != RolUsuarioEnum.PROFESIONAL) {
            throw new RolIncorrectoException(RolUsuarioEnum.PROFESIONAL, rolUsuarioEnum);
        }

        //todo: falta verificacion de email ver como poder anexarlo con crear un usuario de usuario Service

        //si el negocio que quiero asignar al profesional no existe, tiro excepcion
        NegocioEntidad negocioEntidad = negocioRepositorio.findById(idNegocio).orElseThrow(()-> new RecursoNoExisteException("Id negocio"));

        // Crear el usuario
        ProfesionalEntidad profesionalEntidad= profesionalMapper.toEntidad(idNegocio,profesionalRequest);

       return profesionalRepositorio.save(profesionalEntidad);
    }

    //GET profesionales de negocio x id
    public List<ProfesionalEntidad> obtenerProfesionalesPorIdNegocio(Long idNegocio) {
        return negocioRepositorio.getNegocioEntidadByIdUsuario(idNegocio).getProfesionales();
    }

}

package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.repositorio.*;
import com.miTurno.backend.request.ProfesionalRequest;
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
    private final CredencialesRepositorio credencialesRepositorio;

    public ProfesionalService(ProfesionalRepositorio profesionalRepositorio, RolRepositorio rolRepositorio, UsuarioService usuarioService, UsuarioMapper usuarioMapper, NegocioRepositorio negocioRepositorio, ProfesionalMapper profesionalMapper, UsuarioRepositorio usuarioRepositorio,CredencialesRepositorio credencialesRepositorio) {
        this.profesionalRepositorio = profesionalRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.profesionalMapper = profesionalMapper;
        this.usuarioRepositorio = usuarioRepositorio;
        this.credencialesRepositorio = credencialesRepositorio;
    }


    //POST profesional

    public Profesional crearUnprofesional(Long idNegocio, ProfesionalRequest profesionalRequest) throws RolIncorrectoException,RecursoNoExisteException {


        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(profesionalRequest.getIdRolUsuario()).get().getRol();

        if (rolUsuarioEnum != RolUsuarioEnum.PROFESIONAL) {
            throw new RolIncorrectoException(RolUsuarioEnum.PROFESIONAL, rolUsuarioEnum);
        }

        //todo: falta verificacion de email ver como poder anexarlo con crear un usuario de usuario Service

        if (credencialesRepositorio.findByEmail(profesionalRequest.getEmail()).isPresent()){
            throw new EmailYaExisteException(profesionalRequest.getEmail());
        }

        //verificar si ya existe un celular, si es asi tira excepcion
        if (credencialesRepositorio.findByTelefono(profesionalRequest.getTelefono()).isPresent()){
            throw new CelularYaExisteException(profesionalRequest.getTelefono());
        }

        //si el negocio que quiero asignar al profesional no existe, tiro excepcion
        NegocioEntidad negocioEntidad = negocioRepositorio.findById(idNegocio).orElseThrow(()-> new RecursoNoExisteException("Id negocio"));

        // Crear el usuario
        ProfesionalEntidad profesionalEntidad= profesionalMapper.toEntidad(idNegocio,profesionalRequest);

       return profesionalMapper.toModel(profesionalRepositorio.save(profesionalEntidad)) ;
    }

    //GET profesionales de negocio x id
    public List<Profesional> obtenerProfesionalesPorIdNegocio(Long idNegocio) {
        return   profesionalMapper.toModelList(negocioRepositorio.getNegocioEntidadByIdUsuario(idNegocio).getProfesionales());
    }
    //GET profesional x id
    public Profesional obtenerUnProfesional(Long idProfesional){
        return profesionalMapper.toModel(profesionalRepositorio.findById(idProfesional).orElseThrow(()->new UsuarioNoExistenteException(idProfesional))) ;
    }

}

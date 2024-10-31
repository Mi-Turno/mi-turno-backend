package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.RolIncorrectoException;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.repositorio.NegocioRepositorio;
import com.miTurno.backend.repositorio.ProfesionalRepositorio;
import com.miTurno.backend.repositorio.RolRepositorio;
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

    public ProfesionalService(ProfesionalRepositorio profesionalRepositorio, RolRepositorio rolRepositorio, UsuarioService usuarioService, UsuarioMapper usuarioMapper, NegocioRepositorio negocioRepositorio, ProfesionalMapper profesionalMapper) {
        this.profesionalRepositorio = profesionalRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.profesionalMapper = profesionalMapper;
    }


    //POST profesional

    public Usuario crearUnprofesional(ProfesionalRequest profesionalRequest) throws RolIncorrectoException,RecursoNoExisteException {


        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(profesionalRequest.getIdRol()).get().getRol();

        if (rolUsuarioEnum != RolUsuarioEnum.PROFESIONAL) {
            throw new RolIncorrectoException(RolUsuarioEnum.PROFESIONAL, rolUsuarioEnum);
        }

        //si el negocio que quiero asignar al profesional no existe, tiro excepcion
        NegocioEntidad negocioEntidad = negocioRepositorio.findById(profesionalRequest.getIdNegocio()).orElseThrow(()-> new RecursoNoExisteException("Id negocio no existe"));

        // Crear UsuarioRequest usando RolEntidad
        UsuarioRequest usuarioRequest = UsuarioRequest.builder()
                .idRol(profesionalRequest.getIdRol())
                .nombre(profesionalRequest.getNombre())
                .apellido(profesionalRequest.getApellido())
                .email(profesionalRequest.getEmail())
                .password(profesionalRequest.getPassword())
                .telefono(profesionalRequest.getTelefono())
                .fechaNacimiento(profesionalRequest.getFechaNacimiento())
                .build();

        // Crear el usuario
        Usuario profesional = usuarioService.crearUnUsuario(usuarioMapper.toModel(usuarioRequest));



        ProfesionalEntidad profesionalEntidad = ProfesionalEntidad.builder()
                .negocioEntidad(negocioEntidad)
                .build();


        return profesionalMapper.toModel(profesionalEntidad);
    }

    public List<Profesional> obtenerProfesionalesPorIdNegocio(Long idNegocio) {
        List<ProfesionalEntidad> profesionalList = profesionalRepositorio.findAllByIdNegocio(idNegocio);

        return profesionalMapper.toModel(profesionalList);
    }
}

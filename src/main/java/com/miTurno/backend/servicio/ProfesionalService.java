package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.DTO.Servicio;
import com.miTurno.backend.DTO.Turno;
import com.miTurno.backend.entidad.*;
import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.mapper.TurnoMapper;
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

    private final ServicioRepositorio servicioRepositorio;
    private final TurnoMapper turnoMapper;

    public ProfesionalService(ProfesionalRepositorio profesionalRepositorio, RolRepositorio rolRepositorio, UsuarioService usuarioService, UsuarioMapper usuarioMapper,
                              NegocioRepositorio negocioRepositorio, ProfesionalMapper profesionalMapper, UsuarioRepositorio usuarioRepositorio, CredencialesRepositorio credencialesRepositorio,
                              ServicioRepositorio servicioRepositorio, TurnoMapper turnoMapper) {
        this.profesionalRepositorio = profesionalRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.profesionalMapper = profesionalMapper;
        this.usuarioRepositorio = usuarioRepositorio;
        this.credencialesRepositorio = credencialesRepositorio;
        this.servicioRepositorio = servicioRepositorio;
        this.turnoMapper = turnoMapper;
    }


    //POST profesional

    public Profesional crearUnprofesional(Long idNegocio, ProfesionalRequest profesionalRequest) throws RolIncorrectoException, RecursoNoExisteException {


        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(profesionalRequest.getIdRolUsuario()).get().getRol();

        if (rolUsuarioEnum != RolUsuarioEnum.PROFESIONAL) {
            throw new RolIncorrectoException(RolUsuarioEnum.PROFESIONAL, rolUsuarioEnum);
        }

        //todo: falta verificacion de email ver como poder anexarlo con crear un usuario de usuario Service

        if (credencialesRepositorio.findByEmail(profesionalRequest.getEmail()).isPresent()) {
            throw new EmailYaExisteException(profesionalRequest.getEmail());
        }

        //verificar si ya existe un celular, si es asi tira excepcion

        if (credencialesRepositorio.findByTelefono(profesionalRequest.getTelefono()).isPresent()){
            throw new TelefonoYaExisteException(profesionalRequest.getTelefono());

        }

        //si el negocio que quiero asignar al profesional no existe, tiro excepcion
        NegocioEntidad negocioEntidad = negocioRepositorio.findById(idNegocio).orElseThrow(() -> new RecursoNoExisteException("Id negocio"));

        // Crear el usuario
        ProfesionalEntidad profesionalEntidad = profesionalMapper.toEntidad(idNegocio, profesionalRequest);

        return profesionalMapper.toModel(profesionalRepositorio.save(profesionalEntidad));
    }

    //GET profesionales de negocio x id
    public List<Profesional> obtenerProfesionalesPorIdNegocio(Long idNegocio) {

        NegocioEntidad negocioEntidad = negocioRepositorio.findById(idNegocio).orElseThrow(()->new UsuarioNoExistenteException(idNegocio));

        return profesionalMapper.toModelList(negocioEntidad.getProfesionales());
    }

    //GET profesionales de negocio x id y estado

    public List<Profesional> obtenerServiciosPorIdNegocioYEstado(Long idNegocio, Boolean estado) {
        return profesionalMapper.toModelList(profesionalRepositorio.findAllByNegocioEntidad_IdUsuarioAndCredenciales_Estado(idNegocio, estado));
    }

    //GET profesional x id
    public Profesional obtenerUnProfesional(Long idProfesional) {
        return profesionalMapper.toModel(profesionalRepositorio.findById(idProfesional).orElseThrow(() -> new UsuarioNoExistenteException(idProfesional)));
    }


    //GET listado de turnos agendados del profesional ("/{idProfesional}/turnos")
    public List<Turno> obtenerTurnosProfesionalPorIdNegocioYIdProfesional(Long idNegocio,Long idProfesional) {

        if (!negocioRepositorio.existsById(idNegocio)){
            throw new UsuarioNoExistenteException(idNegocio);
        }

        if (!profesionalRepositorio.existsById(idProfesional)){
            throw new UsuarioNoExistenteException(idProfesional);
        }


        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findByIdUsuarioAndNegocioEntidad_IdUsuario(idProfesional,idNegocio);

        return turnoMapper.toModelList(profesionalEntidad.getTurnosAgendados());
    }

    //PUT profesional X id

    public Profesional actualizarProfesional(Long idNegocio, Long idProfesionalAActualizar, Profesional nuevoProfesional) throws ServicioNoExisteException {


        ProfesionalEntidad profesionalEntidad = profesionalRepositorio.findByIdUsuarioAndNegocioEntidad_IdUsuario(idProfesionalAActualizar, idNegocio);

        CredencialesEntidad credencialesEntidad = profesionalEntidad.getCredenciales();
        credencialesEntidad.setEmail(nuevoProfesional.getEmail());
        credencialesEntidad.setTelefono(nuevoProfesional.getTelefono());
        profesionalEntidad.setNombre(nuevoProfesional.getNombre());
        profesionalEntidad.setApellido(nuevoProfesional.getApellido());
        profesionalEntidad.setCredenciales(credencialesEntidad);
        //servicioEntidad.setEstado(nuevoServicio.getEstado());

        profesionalRepositorio.save(profesionalEntidad);

        return profesionalMapper.toModel(profesionalEntidad);
    }

    public Profesional asignarUnServicio(Long idProfesional, Long idServicio) {

        //Busco los recursos
        ProfesionalEntidad profesionalEntidad = profesionalRepositorio.findById(idProfesional).orElseThrow(() -> new RecursoNoExisteException("Profesional con ID " + idProfesional + " no existe"));
        ServicioEntidad servicioEntidad = servicioRepositorio.findById(idServicio).orElseThrow(() -> new RecursoNoExisteException("Servicio con ID " + idServicio + " no existe"));
        //Obtengo los servicios del profesional y los profesionales del servicio

        List<ServicioEntidad> listaServicios = profesionalEntidad.getListaServiciosEntidad();
        List<ProfesionalEntidad> listaProfesionales = servicioEntidad.getProfesionales();

        //los matcheo
        if (!listaServicios.contains(servicioEntidad)) {
            listaServicios.add(servicioEntidad);
            // Agrego el servicio al profesional
        } else {
            // Elimino el servicio del profesional
            listaServicios.remove(servicioEntidad);
        }

        if (!listaProfesionales.contains(profesionalEntidad)) {
            listaProfesionales.add(profesionalEntidad); // Agrego el profesional al servicio
        } else {
            listaProfesionales.remove(profesionalEntidad);
        }

        //los guardo
        servicioRepositorio.save(servicioEntidad);
        return profesionalMapper.toModel(profesionalRepositorio.save(profesionalEntidad));
    }


    public Boolean eliminarUnProfesional(Long idNegocio,Long idProfesionalAEliminar) throws ServicioNoExisteException{
        Boolean rta = true;

        // Busca la entidad de credenciales por ID
        CredencialesEntidad profesionalEntidad = credencialesRepositorio.findById(idProfesionalAEliminar)
                .orElseThrow(() -> new ServicioNoExisteException(idProfesionalAEliminar));

        // Cambia el estado a false para desactivarlo
        profesionalEntidad.setEstado(false);

        // Guarda los cambios
        credencialesRepositorio.save(profesionalEntidad);
        return rta;
    }

}

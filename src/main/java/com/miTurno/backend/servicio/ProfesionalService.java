package com.miTurno.backend.servicio;
import com.miTurno.backend.data.domain.*;
import com.miTurno.backend.data.repositorio.*;
import com.miTurno.backend.data.dtos.response.Profesional;
import com.miTurno.backend.data.dtos.response.Turno;
import com.miTurno.backend.excepciones.*;
import com.miTurno.backend.data.mapper.ProfesionalMapper;
import com.miTurno.backend.data.mapper.TurnoMapper;
import com.miTurno.backend.data.dtos.request.ProfesionalRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesionalService {

    private final ProfesionalRepositorio profesionalRepositorio;
    private final RolRepositorio rolRepositorio;
    private final NegocioRepositorio negocioRepositorio;
    private final ProfesionalMapper profesionalMapper;
    private final CredencialesRepositorio credencialesRepositorio;
    private final ServicioRepositorio servicioRepositorio;
    private final TurnoMapper turnoMapper;

    public ProfesionalService(ProfesionalRepositorio profesionalRepositorio, RolRepositorio rolRepositorio,
                              NegocioRepositorio negocioRepositorio, ProfesionalMapper profesionalMapper, CredencialesRepositorio credencialesRepositorio,
                              ServicioRepositorio servicioRepositorio, TurnoMapper turnoMapper) {

        this.profesionalRepositorio = profesionalRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.negocioRepositorio = negocioRepositorio;
        this.profesionalMapper = profesionalMapper;
        this.credencialesRepositorio = credencialesRepositorio;
        this.servicioRepositorio = servicioRepositorio;
        this.turnoMapper = turnoMapper;
    }


    //POST profesional

    public Profesional crearUnprofesional(Long idNegocio, ProfesionalRequest profesionalRequest)
            throws RolIncorrectoException, EntityNotFoundException, EntityExistsException {

        if (profesionalRequest.getRolUsuario() != RolUsuarioEnum.PROFESIONAL) {
            throw new RolIncorrectoException(RolUsuarioEnum.PROFESIONAL, profesionalRequest.getRolUsuario());
        }

        if (credencialesRepositorio.findByEmail(profesionalRequest.getCredencial().getEmail()).isPresent()) {
            throw new EntityExistsException("El profesional con el email: "+profesionalRequest.getCredencial().getEmail()+" ya existe.");
        }

        if (credencialesRepositorio.findByTelefono(profesionalRequest.getCredencial().getTelefono()).isPresent()){
            throw new EntityExistsException("El profesional con el telefono: "+profesionalRequest.getCredencial().getTelefono()+" ya existe.");

        }

        //si el negocio que quiero asignar al profesional no existe, tiro excepcion
        NegocioEntidad negocioEntidad = negocioRepositorio.findById(idNegocio)
                .orElseThrow(() -> new EntityNotFoundException("Negocio con id: "+ idNegocio+" no encontrado."));

        //buscamos el rol
        RolEntidad rolEntidad = rolRepositorio.findByRol(profesionalRequest.getRolUsuario());


        // Crear el usuario
        ProfesionalEntidad profesionalEntidad = profesionalMapper.toEntidad(profesionalRequest,negocioEntidad,rolEntidad);

        //agrego el profesional al listado del negocio
        negocioEntidad.getProfesionales().add(profesionalEntidad);

        return profesionalMapper.toModel(profesionalRepositorio.save(profesionalEntidad));
    }

    //GET profesionales de negocio x id
    public List<Profesional> obtenerProfesionalesPorIdNegocio(Long idNegocio) throws EntityNotFoundException{

        NegocioEntidad negocioEntidad = negocioRepositorio.findById(idNegocio)
                .orElseThrow(()->new EntityNotFoundException("Negocio con id: "+ idNegocio+" no encontrado."));

        return profesionalMapper.toModelList(negocioEntidad.getProfesionales());
    }

    //GET profesionales de negocio x id y estado

    public List<Profesional> obtenerServiciosPorIdNegocioYEstado(Long idNegocio, Boolean estado) {
        return profesionalMapper.toModelList(profesionalRepositorio.findAllByNegocioEntidadIdAndCredencial_Estado(idNegocio, estado));
    }

    //GET profesional x id
    public Profesional obtenerUnProfesional(Long idProfesional) throws EntityNotFoundException{
        return profesionalMapper.toModel(profesionalRepositorio.findById(idProfesional)
                .orElseThrow(() -> new EntityNotFoundException("Profesional con id: "+ idProfesional+" no encontrado.")));
    }


    //GET listado de turnos agendados del profesional ("/{idProfesional}/turnos")
    public List<Turno> obtenerTurnosProfesionalPorIdNegocioYIdProfesional(Long idNegocio,Long idProfesional)  throws EntityNotFoundException{

        if (!negocioRepositorio.existsById(idNegocio)){
            throw new EntityNotFoundException("Cliente con id: "+ idNegocio+" no encontrado.");
        }

        if (!profesionalRepositorio.existsById(idProfesional)){
            throw new EntityNotFoundException("Cliente con id: "+ idProfesional+" no encontrado.");
        }


        ProfesionalEntidad profesionalEntidad= profesionalRepositorio.findByIdAndNegocioEntidadId(idProfesional,idNegocio);

        return turnoMapper.toModelList(profesionalEntidad.getTurnosAgendados());
    }

    //PUT profesional X id

    public Profesional actualizarProfesional(Long idNegocio, Long idProfesionalAActualizar, Profesional nuevoProfesional) {


        ProfesionalEntidad profesionalEntidad = profesionalRepositorio.findByIdAndNegocioEntidadId(idProfesionalAActualizar, idNegocio);

        CredencialEntidad credencialEntidad = profesionalEntidad.getCredencial();
        credencialEntidad.setEmail(nuevoProfesional.getCredencial().getEmail());
        credencialEntidad.setTelefono(nuevoProfesional.getCredencial().getTelefono());
        profesionalEntidad.setNombre(nuevoProfesional.getNombre());
        profesionalEntidad.setApellido(nuevoProfesional.getApellido());
        profesionalEntidad.setCredencial(credencialEntidad);

        profesionalRepositorio.save(profesionalEntidad);

        return profesionalMapper.toModel(profesionalEntidad);
    }

    public Profesional asignarUnServicio(Long idProfesional, Long idServicio) throws EntityNotFoundException {

        //Busco los recursos
        ProfesionalEntidad profesionalEntidad = profesionalRepositorio.findById(idProfesional)

                .orElseThrow(() -> new EntityNotFoundException("Profesional con id: " + idProfesional + " no encontrado."));

        ServicioEntidad servicioEntidad = servicioRepositorio.findById(idServicio)
                .orElseThrow(() -> new EntityNotFoundException("Servicio con id: " + idServicio + " no encontrado."));

        //Obtengo los servicios del profesional y los profesionales del servicio
        List<ServicioEntidad> listaServicios = profesionalEntidad.getListaServiciosEntidad();
        List<ProfesionalEntidad> listaProfesionales = servicioEntidad.getProfesionales();

        //los matcheo
        if (!listaServicios.contains(servicioEntidad)) {
            // Agrego el servicio al profesional
            listaServicios.add(servicioEntidad);
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


    public Boolean eliminarUnProfesional(Long idNegocio,Long idProfesionalAEliminar) throws EntityNotFoundException{
        Boolean rta = true;

        // Busca la entidad de credenciales por ID
        CredencialEntidad profesionalEntidad = credencialesRepositorio.findById(idProfesionalAEliminar)
                .orElseThrow(() -> new EntityNotFoundException("Profesional con id: " + idProfesionalAEliminar + " no encontrado."));

        // Cambia el estado a false para desactivarlo
        profesionalEntidad.setEstado(false);

        // Guarda los cambios
        credencialesRepositorio.save(profesionalEntidad);
        return rta;
    }

}

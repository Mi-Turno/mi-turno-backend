package com.miTurno.backend.servicio;

import com.miTurno.backend.model.Profesional;
import com.miTurno.backend.model.Servicio;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.mapper.ServicioMapper;
import com.miTurno.backend.repositorio.NegocioRepositorio;
import com.miTurno.backend.repositorio.ServicioRepositorio;
import com.miTurno.backend.request.ServicioRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepositorio servicioRepositorio;
    private final ServicioMapper servicioMapper;

    private final NegocioRepositorio negocioRepositorio;
    private final ProfesionalMapper profesionalMapper;

    @Autowired
    public ServicioService(ServicioRepositorio servicioRepositorio, ServicioMapper servicioMapper, NegocioRepositorio negocioRepositorio, ProfesionalMapper profesionalMapper) {
        this.servicioRepositorio = servicioRepositorio;
        this.servicioMapper= servicioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.profesionalMapper = profesionalMapper;
    }


    //GET uno x ID
    public Servicio obtenerUnServicioPorIdYPorIdNegocio(Long idNegocio, Long idServicio){

        return servicioMapper.toModel(servicioRepositorio.findByNegocioEntidadIdAndId(idNegocio,idServicio));
    }
    //GET all x id

    public List<Servicio> obtenerListadoDeServiciosPorIdNegocio(Long idNegocio){

        return servicioMapper.toModelList(servicioRepositorio.findAllByNegocioEntidadId(idNegocio));
    }

    //GET listado profesionales que dan el servicio x IdServicio

    public List<Profesional> obtenerListadoDeProfesionalesPorIdServicioYIdNegocio(Long idServicio,Long idNegocio){
        ServicioEntidad servicioEntidad = servicioRepositorio.getServicioEntidadByNegocioEntidadIdAndId(idNegocio,idServicio);
        return profesionalMapper.toModelList(servicioEntidad.getProfesionales());
    }
    //POST
    public Servicio crearUnServicio(Long idNegocio,ServicioRequest nuevoServicio){

        //todo buscar el negocioEntidad aca idNegocio
        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio).orElseThrow(()-> new UsuarioNoExistenteException(idNegocio));
        ServicioEntidad servicioEntidad =servicioMapper.toEntidad(negocioEntidad,nuevoServicio);


        // Agregar el servicio a la lista de servicios del negocio
        negocioEntidad.getServicios().add(servicioEntidad);
        return servicioMapper.toModel(servicioRepositorio.save(servicioEntidad));
    }


    public List<Servicio> obtenerServiciosPorIdNegocioYEstado(Long idNegocio, Boolean estado) {

        return servicioMapper.toModelList(servicioRepositorio.findByNegocioEntidadIdAndEstado(idNegocio, estado));
    }

    //DELETE
    public Boolean eliminarUnServicio(Long idNegocio,Long idServicioAEliminar) throws ServicioNoExisteException{
        Boolean rta = true;

        ServicioEntidad servicioEntidad = servicioRepositorio.findByIdAndNegocioEntidadId(idServicioAEliminar, idNegocio);
        //si se encuntra el servicio
        servicioEntidad.setEstado(false);
        servicioRepositorio.save(servicioEntidad);
        return rta;
    }

    //UPDATE

   public Servicio actualizarUnServicio(Long idNegocio,Long idServicioAActualizar,ServicioRequest nuevoServicio) throws ServicioNoExisteException{
        ServicioEntidad servicioEntidad= servicioRepositorio.findByIdAndNegocioEntidadId(idServicioAActualizar,idNegocio);

        servicioEntidad.setDuracion(nuevoServicio.getDuracion());
        servicioEntidad.setPrecio(nuevoServicio.getPrecio());
        //servicioEntidad.setEstado(nuevoServicio.getEstado());
        servicioEntidad.setNombre(nuevoServicio.getNombre());

        servicioRepositorio.save(servicioEntidad);

        return servicioMapper.toModel(servicioEntidad);
    }
}

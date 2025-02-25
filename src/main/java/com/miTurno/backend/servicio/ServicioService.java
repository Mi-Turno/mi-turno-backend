package com.miTurno.backend.servicio;

import com.miTurno.backend.data.domain.ProfesionalEntidad;
import com.miTurno.backend.data.dtos.response.Profesional;
import com.miTurno.backend.data.dtos.response.Servicio;
import com.miTurno.backend.data.domain.NegocioEntidad;
import com.miTurno.backend.data.domain.ServicioEntidad;
import com.miTurno.backend.data.mapper.ProfesionalMapper;
import com.miTurno.backend.data.mapper.ServicioMapper;
import com.miTurno.backend.data.repositorio.NegocioRepositorio;
import com.miTurno.backend.data.repositorio.ProfesionalRepositorio;
import com.miTurno.backend.data.repositorio.ServicioRepositorio;
import com.miTurno.backend.data.dtos.request.ServicioRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    private final ServicioRepositorio servicioRepositorio;
    private final ServicioMapper servicioMapper;
    private final NegocioRepositorio negocioRepositorio;
    private final ProfesionalMapper profesionalMapper;
    private final ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    public ServicioService(ServicioRepositorio servicioRepositorio, ServicioMapper servicioMapper, NegocioRepositorio negocioRepositorio, ProfesionalMapper profesionalMapper, ProfesionalRepositorio profesionalRepositorio) {
        this.servicioRepositorio = servicioRepositorio;
        this.servicioMapper= servicioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.profesionalMapper = profesionalMapper;
        this.profesionalRepositorio = profesionalRepositorio;
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

        List<ProfesionalEntidad> profesionalEntidadList = profesionalRepositorio.findAllByNegocioEntidadIdAndCredencial_Estado(idNegocio,true);


        //recorro cada servicio de cada profesional de la lista, y si ofrece ese servicio lo guardo en la nueva lista
        List<ProfesionalEntidad> listadoProfesionalesConServicioEspecifico = profesionalEntidadList.stream()
                .filter(unProfesional -> unProfesional.getListaServiciosEntidad()
                        .stream().anyMatch(unServicio -> unServicio.getId().equals(idServicio)))
                .collect(Collectors.toList());


        return profesionalMapper.toModelList(listadoProfesionalesConServicioEspecifico);
    }


    //POST
    public Servicio crearUnServicio(Long idNegocio,ServicioRequest nuevoServicio) throws EntityNotFoundException {


        NegocioEntidad negocioEntidad=negocioRepositorio.findById(idNegocio)
                .orElseThrow(()-> new EntityNotFoundException("Negocio con id: "+ idNegocio +" no encontrado."));

        ServicioEntidad servicioEntidad =servicioMapper.toEntidad(negocioEntidad,nuevoServicio);

        // Agregar el servicio a la lista de servicios del negocio
        negocioEntidad.getServicios().add(servicioEntidad);
        return servicioMapper.toModel(servicioRepositorio.save(servicioEntidad));
    }


    public List<Servicio> obtenerServiciosPorIdNegocioYEstado(Long idNegocio, Boolean estado) {

        return servicioMapper.toModelList(servicioRepositorio.findByNegocioEntidadIdAndEstado(idNegocio, estado));
    }

    //DELETE
    public Boolean eliminarUnServicio(Long idNegocio,Long idServicioAEliminar) throws EntityNotFoundException{
        Boolean rta = true;

        ServicioEntidad servicioEntidad = servicioRepositorio.findByIdAndNegocioEntidadId(idServicioAEliminar, idNegocio)
                .orElseThrow(() -> new EntityNotFoundException("Servicio con id: "+ idServicioAEliminar +" no encontrado."));
        //si se encuntra el servicio
        servicioEntidad.setEstado(false);
        servicioRepositorio.save(servicioEntidad);
        return rta;
    }

    //UPDATE

   public Servicio actualizarUnServicio(Long idNegocio,Long idServicioAActualizar,ServicioRequest nuevoServicio) throws EntityNotFoundException{

        ServicioEntidad servicioEntidad= servicioRepositorio.findByIdAndNegocioEntidadId(idServicioAActualizar,idNegocio)
                .orElseThrow(() -> new EntityNotFoundException("Servicio con id: "+ idServicioAActualizar +" no encontrado."));

        servicioEntidad.setDuracion(nuevoServicio.getDuracion());
        servicioEntidad.setPrecio(nuevoServicio.getPrecio());
        //servicioEntidad.setEstado(nuevoServicio.getEstado());
        servicioEntidad.setNombre(nuevoServicio.getNombre());

        servicioRepositorio.save(servicioEntidad);

        return servicioMapper.toModel(servicioEntidad);
    }
}

package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Servicio;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
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
    @Autowired
    public ServicioService(ServicioRepositorio servicioRepositorio, ServicioMapper servicioMapper,NegocioRepositorio negocioRepositorio) {
        this.servicioRepositorio = servicioRepositorio;
        this.servicioMapper= servicioMapper;
        this.negocioRepositorio = negocioRepositorio;

    }

    //GET all


    /*public List<ServicioEntidad> obtenerListadoTodosLosServiciosPorNegocio(Long idNegocio){
        return servicioRepositorio.findAllByIdNegocio(idNegocio);
    }
*/
    //GET x criterio
//
   /* public List<Servicio> obtenerListadoServicios(String nombre, Boolean estado){
        //todo: Hay que hacer un repo custom, y llamar ese metodo.
        return servicioRepositorio.findAll().stream()
                .filter(servicioEntidad -> nombre == null || servicioEntidad.getNombre().toUpperCase().contains(nombre.toUpperCase()))
                .filter(servicioEntidad -> estado == null || (servicioEntidad.getEstado() != null && servicioEntidad.getEstado().equals(estado)))
                .map(servicioMapper::toModel).toList();
    }*/


    //GET uno x ID
    public Servicio obtenerUnServicioPorIdYPorIdNegocio(Long idNegocio, Long idServicio){

        return servicioMapper.toModel(servicioRepositorio.findByNegocioEntidad_IdUsuarioAndIdServicio(idNegocio,idServicio));
    }
    //GET all x id

    public List<Servicio> obtenerListadoDeServiciosPorIdNegocio(Long idNegocio){

        return servicioMapper.toModelList(servicioRepositorio.findAllByNegocioEntidad_IdUsuario(idNegocio));
    }


    //POST
    public Servicio crearUnServicio(Long idNegocio,ServicioRequest nuevoServicio){

        return servicioMapper.toModel(servicioRepositorio.save(servicioMapper.toEntidad(idNegocio,nuevoServicio)));
    }


    public List<Servicio> obtenerServiciosPorIdNegocioYEstado(Long idNegocio, Boolean estado) {

        return servicioMapper.toModelList(servicioRepositorio.findByNegocioEntidad_IdUsuarioAndEstado(idNegocio, estado));
    }

    //DELETE
    public Boolean eliminarUnServicio(Long idNegocio,Long idServicioAEliminar) throws ServicioNoExisteException{
        Boolean rta = true;

        ServicioEntidad servicioEntidad = servicioRepositorio.findByIdServicioAndNegocioEntidad_IdUsuario(idServicioAEliminar, idNegocio);
        //si se encuntra el servicio
        servicioEntidad.setEstado(false);
        servicioRepositorio.save(servicioEntidad);
        return rta;
    }

    //UPDATE

   public Servicio actualizarUnServicio(Long idNegocio,Long idServicioAActualizar,Servicio nuevoServicio) throws ServicioNoExisteException{
        ServicioEntidad servicioEntidad= servicioRepositorio.findByIdServicioAndNegocioEntidad_IdUsuario(idServicioAActualizar,idNegocio);
        servicioEntidad.setDuracion(nuevoServicio.getDuracion());
        servicioEntidad.setPrecio(nuevoServicio.getPrecio());
        //servicioEntidad.setEstado(nuevoServicio.getEstado());
        servicioEntidad.setNombre(nuevoServicio.getNombre());

        servicioRepositorio.save(servicioEntidad);

        return servicioMapper.toModel(servicioEntidad);
    }
}

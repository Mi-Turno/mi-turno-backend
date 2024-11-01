package com.miTurno.backend.servicio;

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
    public ServicioEntidad obtenerUnServicioPorId(Long idNegocio,Long idServicio){

        return servicioRepositorio.findByNegocioEntidad_IdUsuarioAndIdServicio(idNegocio,idServicio);
    }
    //GET all x id

    public List<ServicioEntidad> obtenerListadoDeServiciosPorIdNegocio(Long idNegocio){

        return servicioRepositorio.findAllByNegocioEntidad_IdUsuario(idNegocio);
    }

    //POST
    public ServicioEntidad crearUnServicio(Long idNegocio,ServicioRequest nuevoServicio){

        return servicioRepositorio.save(servicioMapper.toEntidad(idNegocio,nuevoServicio));
    }


    //DELETE
    public Boolean eliminarUnServicio(Long idNegocio,Long idServicioAEliminar) throws ServicioNoExisteException{
        Boolean rta = true;

        ServicioEntidad servicioEntidad = servicioRepositorio.findById(idServicioAEliminar).orElseThrow(()-> new ServicioNoExisteException((idServicioAEliminar)));

        //si se encuntra el servicio
        servicioEntidad.setEstado(false);
        servicioRepositorio.save(servicioEntidad);

        return rta;
    }

    //UPDATE
   /* public Servicio actualizarUnServicio(Long idNegocio,Long idServicioAActualizar,Servicio nuevoServicio) throws ServicioNoExisteException{
        ServicioEntidad servicioEntidad= servicioRepositorio.findByIdNegocioAndIdServicio(idNegocio,idServicioAActualizar);

        servicioEntidad.setDuracion(nuevoServicio.getDuracion());
        //todo: El precio no debería estar aca sinó que depende de cada profesional
        //servicioEntidad.setPrecio(nuevoServicio.getPrecio());
        //servicioEntidad.setEstado(nuevoServicio.getEstado());
        servicioEntidad.setNombre(nuevoServicio.getNombre());

        servicioRepositorio.save(servicioEntidad);

        return servicioMapper.toModel(servicioEntidad);
    }*/
}

package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.mapper.ServicioMapper;
import com.miTurno.backend.modelo.Servicio;
import com.miTurno.backend.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepositorio servicioRepositorio;
    private final ServicioMapper servicioMapper;


    @Autowired
    public ServicioService(ServicioRepositorio servicioRepositorio, ServicioMapper servicioMapper) {
        this.servicioRepositorio = servicioRepositorio;
        this.servicioMapper= servicioMapper;
       
    }

    //GET all

    public List<ServicioEntidad> obtenerListadoTodosLosServicios(){
        return servicioRepositorio.findAll();
    }

    //GET x criterio
//
    public List<Servicio> obtenerListadoServicios(String nombre, Boolean estado){
        //todo: Hay que hacer un repo custom, y llamar ese metodo.
        return servicioRepositorio.findAll().stream()
                .filter(servicioEntidad -> nombre == null || servicioEntidad.getNombre().toUpperCase().contains(nombre.toUpperCase()))
                .filter(servicioEntidad -> estado == null || (servicioEntidad.getEstado() != null && servicioEntidad.getEstado().equals(estado)))
                .map(servicioMapper::toModel).toList();
    }


    //GET ID

    //POST
    public Servicio crearUnServicio(Servicio nuevoServicio){
        ServicioEntidad servicioEntidad= new ServicioEntidad();

        servicioEntidad.setDuracion(nuevoServicio.getDuracion());
        servicioEntidad.setNombre(nuevoServicio.getNombre());
        servicioEntidad.setEstado(true);
        servicioEntidad.setPrecio(0.0);
        servicioRepositorio.save(servicioEntidad);
        return servicioMapper.toModel(servicioEntidad);
    }

    //DELETE
    public Boolean eliminarUnServicio(Long idServicioAEliminar) throws ServicioNoExisteException{
        Boolean rta = true;

        ServicioEntidad servicioEntidad = servicioRepositorio.findById(idServicioAEliminar).orElseThrow(()-> new ServicioNoExisteException((idServicioAEliminar)));

        //si se encuntra el servicio
        servicioEntidad.setEstado(false);
        servicioRepositorio.save(servicioEntidad);

        return rta;
    }

    //UPDATE
    public Servicio actualizarUnServicio(Long idServicioAActualizar,Servicio nuevoServicio) throws ServicioNoExisteException{
        ServicioEntidad servicioEntidad= servicioRepositorio.findById(idServicioAActualizar).orElseThrow(()->new ServicioNoExisteException(idServicioAActualizar));
        servicioEntidad.setDuracion(nuevoServicio.getDuracion());
        //todo: El precio no debería estar aca sinó que depende de cada profesional
        //servicioEntidad.setPrecio(nuevoServicio.getPrecio());
        //servicioEntidad.setEstado(nuevoServicio.getEstado());
        servicioEntidad.setNombre(nuevoServicio.getNombre());

        servicioRepositorio.save(servicioEntidad);

        return servicioMapper.toModel(servicioEntidad);
    }
}

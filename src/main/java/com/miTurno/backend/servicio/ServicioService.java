package com.miTurno.backend.servicio;

import com.miTurno.backend.controlador.ServicioControlador;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.mapper.ServicioMapper;
import com.miTurno.backend.modelo.Servicio;
import com.miTurno.backend.repositorio.ServicioRepositorio;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepositorio servicioRepositorio;
    private final ServicioMapper servicioMapper;
    private final ServicioControlador servicioControlador;

    @Autowired
    public ServicioService(ServicioRepositorio servicioRepositorio, ServicioMapper servicioMapper, ServicioControlador servicioControlador) {
        this.servicioRepositorio = servicioRepositorio;
        this.servicioMapper= servicioMapper;
        this.servicioControlador = servicioControlador;
    }

    //GET all

    public List<ServicioEntidad> obtenerListadoTodosLosServicios(){
        return servicioRepositorio.findAll();
    }

    //GET x criterio

    public List<Servicio> obtenerListadoServicios(String nombreServicio, Double precio, Integer duracion){
        //todo: Hay que hacer un repo custom, y llamar ese metodo.
        return servicioRepositorio.findAll().stream().map(servicioMapper::toModel).toList();
    }

    //GET ID

    //POST
    public Servicio crearUnServicio(Servicio nuevoServicio){
        ServicioEntidad servicioEntidad= new ServicioEntidad();

        servicioEntidad.setDuracion(nuevoServicio.getDuracion());
        servicioEntidad.setNombre(nuevoServicio.getNombre());
        servicioEntidad.setEstado(true);
        servicioEntidad.setPrecio(nuevoServicio.getPrecio());
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
        servicioEntidad.setPrecio(nuevoServicio.getPrecio());
        servicioEntidad.setEstado(nuevoServicio.getEstado());
        servicioEntidad.setNombre(nuevoServicio.getNombre());

        servicioRepositorio.save(servicioEntidad);

        return servicioMapper.toModel(servicioEntidad);
    }
}

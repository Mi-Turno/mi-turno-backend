package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.mapper.ServicioMapper;
import com.miTurno.backend.modelo.Servicio;
import com.miTurno.backend.repositorio.ServicioRepositorio;
import com.miTurno.backend.repositorio.pivotRepositorios.ServicioXProfesionalesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepositorio servicioRepositorio;
    private final ServicioMapper servicioMapper;
    private final ServicioXProfesionalesRepositorio servicioXProfesionalesRepositorio;


    @Autowired
    public ServicioService(ServicioRepositorio servicioRepositorio, ServicioMapper servicioMapper, ServicioXProfesionalesRepositorio servicioXProfesionalesRepositorio) {
        this.servicioRepositorio = servicioRepositorio;
        this.servicioMapper= servicioMapper;
        this.servicioXProfesionalesRepositorio = servicioXProfesionalesRepositorio;
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

    public List<Servicio> obtenerServiciosPorIdProfesional(Long idProfesional){
        List<Long> idServiciosList= servicioXProfesionalesRepositorio.findAllServiciosByProfesional(idProfesional);

        List<ServicioEntidad> servicioEntidadList = idServiciosList.stream()
                .map(servicioRepositorio::findById)
                .flatMap(Optional::stream)// Extrae los valores presentes
                .toList();

        return servicioEntidadList.stream().map(servicioMapper::toModel).toList();
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

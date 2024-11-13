package com.miTurno.backend.mapper;

import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.repositorio.NegocioRepositorio;
import com.miTurno.backend.request.ServicioRequest;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.DTO.Servicio;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ServicioMapper {

    private final NegocioRepositorio negocioRepositorio;

    public ServicioMapper(NegocioRepositorio negocioRepositorio) {
        this.negocioRepositorio = negocioRepositorio;
    }

    //entidad a servicio
    public Servicio toModel(ServicioEntidad servicioEntidad){
        return  Servicio.builder()
                .idServicio(servicioEntidad.getId())
                .idNegocio(servicioEntidad.getNegocioEntidad().getId())
                .duracion(servicioEntidad.getDuracion())
                .estado(servicioEntidad.getEstado())
                .precio(servicioEntidad.getPrecio())
                .nombre(servicioEntidad.getNombre())
                .build();
    }
    public List<Servicio> toModelList(List<ServicioEntidad> listaServicioEntidad) {
        // Si la lista es null, retorna una lista vacía en lugar de null
        if (listaServicioEntidad == null) {
            return Collections.emptyList();
        }

        return listaServicioEntidad.stream()
                .map(this::toModel)
                .toList();
    }
    //request a servicio
    public Servicio toModel(ServicioRequest servicioRequest){
        return Servicio.builder()
//                .idNegocio(servicioRequest.getIdNegocio()) lo comento porque el idNegocio esta en el pathvariable
                .duracion(servicioRequest.getDuracion())
                .precio(servicioRequest.getPrecio())
                .nombre(servicioRequest.getNombre())
                .build();
    }

    //request a entidad
    public ServicioEntidad toEntidad(Long idNegocio,ServicioRequest servicioRequest) {
        ServicioEntidad servicioEntidad= new ServicioEntidad();

        NegocioEntidad negocioEntidad = negocioRepositorio.findById(idNegocio).orElseThrow(()->new UsuarioNoExistenteException(idNegocio));
        servicioEntidad.setNegocioEntidad(negocioEntidad);

        // Agregar el servicio a la lista de servicios del negocio
        negocioEntidad.getServicios().add(servicioEntidad);

        servicioEntidad.setDuracion(servicioRequest.getDuracion());
        servicioEntidad.setNombre(servicioRequest.getNombre());
        servicioEntidad.setEstado(true);
        servicioEntidad.setPrecio(servicioRequest.getPrecio());

        return servicioEntidad;
    }

    //Entidad to modelo
   /* public Servicio toModel(ServicioEntidad servicioEntidad){
        return Servicio.builder()
                .idNegocio(servicioEntidad.getIdServicio())
                .idServicio(servicioEntidad.getIdServicio())
                .nombre(servicioEntidad.getNombre())
                .duracion(servicioEntidad.getDuracion())
                .precio(servicioEntidad.getPrecio())
                .estado(servicioEntidad.getEstado())
                .build();
    }*/


}

package com.miTurno.backend.data.mapper;

import com.miTurno.backend.data.domain.NegocioEntidad;
import com.miTurno.backend.data.dtos.request.ServicioRequest;
import com.miTurno.backend.data.domain.ServicioEntidad;
import com.miTurno.backend.data.dtos.response.Servicio;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ServicioMapper {



    public ServicioMapper() {
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
                .fotoServicio(servicioEntidad.getFotoServicio())
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
    public ServicioEntidad toEntidad(NegocioEntidad negocioEntidad,ServicioRequest servicioRequest) {

        return ServicioEntidad.builder()
                .negocioEntidad(negocioEntidad)
                .duracion(servicioRequest.getDuracion())
                .nombre(servicioRequest.getNombre())
                .estado(true)
                .precio(servicioRequest.getPrecio())
                .build();




    }




}

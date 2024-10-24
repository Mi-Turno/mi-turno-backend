package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.ServicioRequest;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.modelo.Servicio;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {

    //entidad a servicio
    public Servicio toModel(ServicioEntidad servicioEntidad){
        return  Servicio.builder()
                .idServicio(servicioEntidad.getIdServicio())
                .duracion(servicioEntidad.getDuracion())
                .estado(servicioEntidad.getEstado())
                .precio(servicioEntidad.getPrecio())
                .nombre(servicioEntidad.getNombre())
                .build();
    }

    //request a servicio
    public Servicio toModel(ServicioRequest servicioRequest){
        return Servicio.builder()
                .duracion(servicioRequest.getDuracion())
                .precio(servicioRequest.getPrecio())
                .nombre(servicioRequest.getNombre())
                .build();
    }
}

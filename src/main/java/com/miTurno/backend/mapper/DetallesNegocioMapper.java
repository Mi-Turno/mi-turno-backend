package com.miTurno.backend.mapper;

import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import com.miTurno.backend.modelo.DetallesNegocio;
import org.springframework.stereotype.Component;

@Component
public class DetallesNegocioMapper {

    //entidad a Negocio
    public DetallesNegocio toModel(DetallesNegocioEntidad negocioEntidad){
        return DetallesNegocio.builder()
                .idDetallesNegocio(negocioEntidad.getIdDetallesNegocio())
                .nombreNegocio(negocioEntidad.getNombreNegocio())
                .rubro(negocioEntidad.getRubro())
                .idNegocio(negocioEntidad.getIdNegocio())
                .build();
    }

}

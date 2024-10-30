package com.miTurno.backend.mapper;

import com.miTurno.backend.DTO.DetallesNegocioRequest;
import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import com.miTurno.backend.modelo.DetallesNegocio;
import org.springframework.stereotype.Component;

@Component
public class DetallesNegocioMapper {

    //entidad a Negocio
    public DetallesNegocio toModel(DetallesNegocioEntidad detallesNegocioEntidad){
        return DetallesNegocio.builder()
                .idDetallesNegocio(detallesNegocioEntidad.getIdDetallesNegocio())
                .rubro(detallesNegocioEntidad.getRubro())
                .calle(detallesNegocioEntidad.getCalle())
                .altura(detallesNegocioEntidad.getAltura())
                .detalle(detallesNegocioEntidad.getDetalle())
                .build();
    }


    //request a negocio
    public DetallesNegocio toModel(DetallesNegocioRequest detallesNegocioRequest){
        return DetallesNegocio.builder()
                .rubro(detallesNegocioRequest.getRubro())
                .calle(detallesNegocioRequest.getCalle())
                .altura(detallesNegocioRequest.getAltura())
                .detalle(detallesNegocioRequest.getDetalle())
                .build();
    }

    //negocio a Entidad
    public DetallesNegocioEntidad toEntidad(DetallesNegocio detallesNegocio){
        return DetallesNegocioEntidad.builder()
                .rubro(detallesNegocio.getRubro())
                .calle(detallesNegocio.getCalle())
                .altura(detallesNegocio.getAltura())
                .detalle(detallesNegocio.getDetalle())
                .build();
    }

}

package com.miTurno.backend.mapper;

import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.DTO.Negocio;
import org.springframework.stereotype.Component;

@Component
public class NegocioMapper {

    //entidad a Negocio
    public Negocio toModel(NegocioEntidad negocioEntidad){
        return Negocio.builder()
                .rubro(negocioEntidad.getRubro())
                .calle(negocioEntidad.getCalle())
                .altura(negocioEntidad.getAltura())
                .detalle(negocioEntidad.getDetalle())
                .build();
    }


    //request a negocio
    public Negocio toModel(NegocioRequest negocioRequest){
        return Negocio.builder()
                .rubro(negocioRequest.getRubro())
                .calle(negocioRequest.getCalle())
                .altura(negocioRequest.getAltura())
                .detalle(negocioRequest.getDetalle())
                .build();
    }

    //negocio a Entidad
    public NegocioEntidad toEntidad(Negocio negocio){
        return NegocioEntidad.builder()
                .rubro(negocio.getRubro())
                .calle(negocio.getCalle())
                .altura(negocio.getAltura())
                .detalle(negocio.getDetalle())
                .build();
    }

}

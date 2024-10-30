package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.DetallesNegocioRequest;
import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import com.miTurno.backend.mapper.DetallesNegocioMapper;
import com.miTurno.backend.modelo.DetallesNegocio;
import com.miTurno.backend.repositorio.DetallesNegocioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallesNegocioService {
    //atributos
    private DetallesNegocioRepositorio detallesNegocioRepositorio;
    private DetallesNegocioMapper detallesNegocioMapper;


    //constructores
    @Autowired
    public DetallesNegocioService(DetallesNegocioMapper detallesNegocioMapper, DetallesNegocioRepositorio detallesNegocioRepositorio) {
        this.detallesNegocioMapper = detallesNegocioMapper;
        this.detallesNegocioRepositorio = detallesNegocioRepositorio;
    }

    //metodos

    //GET detalles negocio

    //GET detalles negocio x id
//    public DetallesNegocio obtenerDetallesNegocioXIdNegocio(Long idNegocio){
//
//    }


    //POST detalles negocio
    public DetallesNegocio crearDetallesNegocio(DetallesNegocioRequest detallesNegocioRequest){
        //lo hago asi para q sea mas semantico

        //primero lo paso a clase modelo
        DetallesNegocio detallesNegocio=detallesNegocioMapper.toModel(detallesNegocioRequest);

        //despues lo paso a entidad
        DetallesNegocioEntidad detallesNegocioEntidad= detallesNegocioMapper.toEntidad(detallesNegocio);

        //y ya despues lo guardo y lo que me retorna .save, lo mappeo
        return detallesNegocioMapper.toModel(detallesNegocioRepositorio.save(detallesNegocioEntidad));
    }



}

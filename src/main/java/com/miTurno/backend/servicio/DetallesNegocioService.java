package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.mapper.DetallesNegocioMapper;
import com.miTurno.backend.mapper.ServicioMapper;
import com.miTurno.backend.modelo.DetallesNegocio;
import com.miTurno.backend.modelo.Servicio;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.repositorio.DetallesNegocioRepositorio;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallesNegocioService {
    private ServicioMapper servicioMapper;
    private UsuarioRepositorio usuarioRepositorio;
    //atributos
    private DetallesNegocioRepositorio detallesNegocioRepositorio;
    private DetallesNegocioMapper detallesNegocioMapper;

    //constructores
    @Autowired
    public DetallesNegocioService(DetallesNegocioMapper detallesNegocioMapper, DetallesNegocioRepositorio detallesNegocioRepositorio, ServicioMapper servicioMapper, UsuarioRepositorio usuarioRepositorio) {
        this.detallesNegocioMapper = detallesNegocioMapper;
        this.detallesNegocioRepositorio = detallesNegocioRepositorio;
        this.servicioMapper = servicioMapper;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    //metodos

    //get all
//    public List<DetallesNegocio> obtenerTodosLosNegocios(){
//        return detallesNegocioRepositorio.findAll().stream().map(detallesNegocioMapper::toModel).toList();
//    }

    //get x id
//    public DetallesNegocio obtenerDetallesNegocioPorId(Long idNegocio){
//        return detallesNegocioRepositorio.findById(idNegocio);
//    }

    //get x nombre
    public DetallesNegocio obtenerDetallesNegocioPorNombre(String nombreNegocio){
        DetallesNegocioEntidad detallesNegocioEntidad= detallesNegocioRepositorio.findByNombreNegocio(nombreNegocio);
        return detallesNegocioMapper.toModel(detallesNegocioEntidad);
    }

    //get servicios x nombre

    public List<Servicio> obtenerServiciosDeNegocioPorNombre(String nombreNegocio){
        List<ServicioEntidad> servicioLista = detallesNegocioRepositorio.findAllServiciosPorNombreNegocio(nombreNegocio);
        return servicioLista.stream().map(servicioMapper::toModel).toList();
    }

    //get profesionales x nombre de negocio
//    public List<Usuario> obtenerProfesionalesDeNegocioPorNombre(String nombreNegocio){
//        List<UsuarioEntidad> usuarioEntidadLista=
//
//    }





    //post detalles negocio

}

package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.entidad.pivotEntidad.ProfesionalesXNegocioEntidad;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.repositorio.pivotRepositorios.ProfesionalesXNegocioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesionalesXNegocioService {
   private final ProfesionalesXNegocioRepositorio profesionalesXNegocioRepositorio;
   private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public ProfesionalesXNegocioService(ProfesionalesXNegocioRepositorio profesionalesXNegocioRepositorio, UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper) {
        this.profesionalesXNegocioRepositorio = profesionalesXNegocioRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
    }

    //obtener todos los profesionales por id negocio

    public List<Usuario> obtenerProfesionalesPorIdNegocio(Long idNegocio){
       List<UsuarioEntidad> usuarioEntidadList = profesionalesXNegocioRepositorio.obtenerTodosLosProfesionalesPorIdNegocio(idNegocio);
        //todo buscar todas las ids de los usuarios



        return usuarioEntidadList.stream().map(usuarioMapper::toModel).toList();
    }

    //

    public Long crearProfesionalPorNegocio(Long idProfesional, Long idNegocio){

        return profesionalesXNegocioRepositorio.save(new ProfesionalesXNegocioEntidad(idProfesional,idNegocio)).getProfesionales_x_negocio();
    }

}

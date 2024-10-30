package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.repositorio.pivotRepositorios.ProfesionalesXNegocioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
       List<Long> idUsuarios = profesionalesXNegocioRepositorio.findAllProfesionalesByIdNegocio(idNegocio);
        //todo buscar todas las ids de los usuarios

        //mapeo los ids
        //para cada id lo busco en el repo
        //esa rta me retorna un optional ya que la entidad puede existir o no, lo q hace flatMap es sacar las que no existen
        //lo convierto en una lista de usuarioEntidad
        List<UsuarioEntidad> usuarioEntidadList = idUsuarios.stream()
               .map(usuarioRepositorio::findById)
               .flatMap(Optional::stream)// Extrae los valores presentes
               .toList();

        return usuarioEntidadList.stream().map(usuarioMapper::toModel).toList();

    }
}

package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Profesional;
import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.excepcion.NombreNegocioYaExisteException;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.RolIncorrectoException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.repositorio.*;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.mapper.NegocioMapper;
import com.miTurno.backend.DTO.Negocio;
import com.miTurno.backend.request.UsuarioRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NegocioService {
    private final RolRepositorio rolRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final CredencialesRepositorio credencialesRepositorio;
    private final ProfesionalRepositorio profesionalRepositorio;
    private final ProfesionalMapper profesionalMapper;
    //atributos
    private NegocioRepositorio negocioRepositorio;
    private NegocioMapper negocioMapper;


    //constructores
    @Autowired
    public NegocioService(NegocioMapper negocioMapper, NegocioRepositorio negocioRepositorio, RolRepositorio rolRepositorio, UsuarioRepositorio usuarioRepositorio, UsuarioService usuarioService, UsuarioMapper usuarioMapper, CredencialesRepositorio credencialesRepositorio, ProfesionalRepositorio profesionalRepositorio, ProfesionalMapper profesionalMapper) {
        this.negocioMapper = negocioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.credencialesRepositorio = credencialesRepositorio;
        this.profesionalRepositorio = profesionalRepositorio;
        this.profesionalMapper = profesionalMapper;
    }

    //metodos

    //GET all negocios
    public List<NegocioEntidad> listarTodosLosNegocios(){
        return negocioRepositorio.findAll();
    }

    //GET negocio x id
    public NegocioEntidad obtenerNegocioPorId(Long idNegocio){
        return negocioRepositorio.findById(idNegocio).orElseThrow(()-> new UsuarioNoExistenteException(idNegocio));
    }

    //obtener negocio x nombre
//    public UsuarioEntidad obtenerNegocioPorNombre(String nombre) throws NombreNoExisteException{
//       return usuarioRepositorio.findByRolEntidad_RolAndNombre(RolUsuarioEnum.NEGOCIO,nombre).orElseThrow(()->new NombreNoExisteException(nombre));
//    }


    //POST negocio
    public NegocioEntidad crearUnNegocio(NegocioRequest negocioRequest)
            throws NombreNegocioYaExisteException, RolIncorrectoException {

        String nombreNegocio = negocioRequest.getNombre();
        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(negocioRequest.getIdRol())
                .orElseThrow(()->new RecursoNoExisteException("No existe ese rol"))
                .getRol();

        if (rolUsuarioEnum != RolUsuarioEnum.NEGOCIO) {
            throw new RolIncorrectoException(RolUsuarioEnum.NEGOCIO, rolUsuarioEnum);
        }

        if (credencialesRepositorio.existsByRolEntidad_RolAndUsuario_Nombre(rolUsuarioEnum,nombreNegocio)) {
            throw new NombreNegocioYaExisteException(nombreNegocio);
        }

        Negocio negocio= negocioMapper.toModel(negocioRequest);

        return negocioRepositorio.save(negocioMapper.toEntidad(negocio));
        //return usuarioService.crearUnUsuario(negocio);
    }



    //todo todas estos metodos van en los servicios correspondientes

    //GET todos los profesionales x id negocio


    //POST profesionales de negocio x id

    //GET servicios de negocio x id


    //POST servicios de negocio x id

    //GET clientes de negocio x id

    //POST clientes de negocio x id




}

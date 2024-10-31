package com.miTurno.backend.servicio;

import com.miTurno.backend.excepcion.NombreNegocioYaExisteException;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.RolIncorrectoException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.mapper.NegocioMapper;
import com.miTurno.backend.DTO.Negocio;
import com.miTurno.backend.repositorio.NegocioRepositorio;
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
    //atributos
    private NegocioRepositorio negocioRepositorio;
    private NegocioMapper negocioMapper;


    //constructores
    @Autowired
    public NegocioService(NegocioMapper negocioMapper, NegocioRepositorio negocioRepositorio, RolRepositorio rolRepositorio, UsuarioRepositorio usuarioRepositorio, UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.negocioMapper = negocioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    //metodos

    //GET all negocios
    public List<Negocio> listarTodosLosNegocios(){
        List<NegocioEntidad> negocioEntidadList = negocioRepositorio.findAll();

        return negocioEntidadList.stream().map(negocioMapper::toModel).toList();
    }

    //GET negocio x id
    public Negocio obtenerNegocioPorId(Long idNegocio){
        NegocioEntidad negocioEntidad= negocioRepositorio.findById(idNegocio).orElseThrow(()-> new UsuarioNoExistenteException(idNegocio));

        return negocioMapper.toModel(negocioEntidad);
    }

    //obtener negocio x nombre
//    public UsuarioEntidad obtenerNegocioPorNombre(String nombre) throws NombreNoExisteException{
//       return usuarioRepositorio.findByRolEntidad_RolAndNombre(RolUsuarioEnum.NEGOCIO,nombre).orElseThrow(()->new NombreNoExisteException(nombre));
//    }


    //POST negocio
    public Negocio crearUnNegocio(NegocioRequest negocioRequest)
            throws NombreNegocioYaExisteException, RolIncorrectoException {

        String nombreNegocio = negocioRequest.getNombre();
        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(negocioRequest.getIdRol())
                .orElseThrow(()->new RecursoNoExisteException("No existe ese rol"))
                .getRol();

        if (rolUsuarioEnum != RolUsuarioEnum.NEGOCIO) {
            throw new RolIncorrectoException(RolUsuarioEnum.NEGOCIO, rolUsuarioEnum);
        }

        if (usuarioRepositorio.existsByNombreAndRolEntidad_Rol(nombreNegocio, rolUsuarioEnum)) {
            throw new NombreNegocioYaExisteException(nombreNegocio);
        }
        Negocio negocio= negocioMapper.toModel(negocioRequest);
        NegocioEntidad negocioEntidad= negocioMapper.toEntidad(negocio);

        usuarioRepositorio.save(negocioEntidad);


        // Crear el usuario negocio

        // Crear los detalles del negocio
        return crearNegocio(negocioRequest);
    }

    //GET profesionales de negocio x id
    public List<ProfesionalEntidad> obtenerProfesionalesPorIdNegocio(Long idNegocio){
        List<ProfesionalEntidad> profesionales = null;

        return profesionales;
    }


    //todo todas estos metodos van en los servicios correspondientes
    //POST profesionales de negocio x id

    //GET servicios de negocio x id


    //POST servicios de negocio x id

    //GET clientes de negocio x id

    //POST clientes de negocio x id

    //POST detalles negocio
    public Negocio crearNegocio(NegocioRequest negocioRequest){

        //primero lo paso a clase modelo
        Negocio negocio = negocioMapper.toModel(negocioRequest);

        //despues lo paso a entidad
        NegocioEntidad negocioEntidad= negocioMapper.toEntidad(negocio);

        //y ya despues lo guardo y lo que me retorna .save, lo mappeo
        return negocioMapper.toModel(negocioRepositorio.save(negocioEntidad));
    }



}

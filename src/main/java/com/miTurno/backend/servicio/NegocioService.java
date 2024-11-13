package com.miTurno.backend.servicio;

import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.mapper.ProfesionalMapper;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.repositorio.*;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.mapper.NegocioMapper;
import com.miTurno.backend.DTO.Negocio;
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
    public List<Negocio> listarTodosLosNegocios(){
        return negocioMapper.toModelList(negocioRepositorio.findAll()) ;
    }



    //GET negocio x id
    public Negocio obtenerNegocioPorId(Long idNegocio){
        return negocioMapper.toModel(negocioRepositorio.findById(idNegocio).orElseThrow(()-> new UsuarioNoExistenteException(idNegocio)));
    }

    //obtener negocio x nombre
    public Negocio obtenerNegocioPorNombre(String nombre) throws NombreNoExisteException {
       return negocioMapper.toModel(negocioRepositorio.getNegocioEntidadByNombreIgnoreCase(nombre).orElseThrow(()->new NombreNoExisteException(nombre)));
    }


    //POST negocio
    public Negocio crearUnNegocio(NegocioRequest negocioRequest)
            throws NombreNegocioYaExisteException, RolIncorrectoException,EmailYaExisteException, TelefonoYaExisteException {

        String nombreNegocio = negocioRequest.getNombre();
        RolUsuarioEnum rolUsuarioEnum = rolRepositorio.findById(negocioRequest.getIdRolUsuario())
                .orElseThrow(()->new RecursoNoExisteException("No existe ese rol"))
                .getRol();

        if (rolUsuarioEnum != RolUsuarioEnum.NEGOCIO) {
            throw new RolIncorrectoException(RolUsuarioEnum.NEGOCIO, rolUsuarioEnum);
        }

        if (negocioRepositorio.existsByNombreAndCredenciales_RolEntidad_Rol(nombreNegocio,rolUsuarioEnum)) {
            throw new NombreNegocioYaExisteException(nombreNegocio);
        }
        if(negocioRepositorio.existsByCredenciales_Email(negocioRequest.getEmail())){
            throw new EmailYaExisteException(negocioRequest.getEmail());
        }
        if(negocioRepositorio.existsByCredenciales_Telefono(negocioRequest.getTelefono())){
            throw new TelefonoYaExisteException(negocioRequest.getTelefono());
        }
        Negocio negocio= negocioMapper.toModel(negocioRequest);

        return negocioMapper.toModel(negocioRepositorio.save(negocioMapper.toEntidad(negocio)));
        //return usuarioService.crearUnUsuario(negocio);
    }

    //GET id negocio x nombre negocio
    public Long obtenerIdNegocioPorNombreNegocio(String nombreNegocio){

        NegocioEntidad negocioEntidad= negocioRepositorio.getNegocioEntidadByNombreIgnoreCase(nombreNegocio).orElseThrow(()-> new NombreNoExisteException("Nombre negocio"));
        return negocioEntidad.getId();
    }

    //GET listado de negocios x nombre parecido
    public List<NegocioEntidad> obtenerListadoDeNegociosConNombreAproximado(String nombreNegocio){

        List<NegocioEntidad> listaNegocioEntidad= negocioRepositorio.getNegocioEntidadsByNombreLikeIgnoreCase(nombreNegocio);
        return listaNegocioEntidad;
    }


    //todo todas estos metodos van en los servicios correspondientes

    //GET todos los profesionales x id negocio


    //POST profesionales de negocio x id

    //GET servicios de negocio x id


    //POST servicios de negocio x id

    //GET clientes de negocio x id

    //POST clientes de negocio x id




}

package com.miTurno.backend.servicio;

import com.miTurno.backend.excepcion.NombreNegocioYaExisteException;
import com.miTurno.backend.excepcion.RecursoNoExisteException;
import com.miTurno.backend.excepcion.RolIncorrectoException;
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
    //atributos
    private NegocioRepositorio negocioRepositorio;
    private NegocioMapper negocioMapper;


    //constructores
    @Autowired
    public NegocioService(NegocioMapper negocioMapper, NegocioRepositorio negocioRepositorio, RolRepositorio rolRepositorio, UsuarioRepositorio usuarioRepositorio, UsuarioService usuarioService) {
        this.negocioMapper = negocioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioService = usuarioService;
    }

    //metodos

    //GET all negocios
    public List<NegocioEntidad> listarTodosLosNegocios(){
        List<NegocioEntidad> negocios = null;

        return negocios;
    }

    //GET negocio x id
    public NegocioEntidad obtenerNegocioPorId(Long idNegocio){
        NegocioEntidad negocioEntidad = null;
        return negocioEntidad;
    }

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


        // Crear UsuarioRequest usando negocioRequest
        UsuarioRequest usuarioRequest = UsuarioRequest.builder()
                .idRol(negocioRequest.getIdRol())
                .nombre(negocioRequest.getNombre())
                .apellido(negocioRequest.getApellido())
                .email(negocioRequest.getEmail())
                .password(negocioRequest.getPassword())
                .telefono(negocioRequest.getTelefono())
                .fechaNacimiento(negocioRequest.getFechaNacimiento())
                .build();


        // Crear el usuario negocio
        usuarioService.crearUnUsuario(usuarioRequest);

        // Crear los detalles del negocio
        return crearDetallesNegocio(negocioRequest);
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
    public Negocio crearDetallesNegocio(NegocioRequest negocioRequest){

        //primero lo paso a clase modelo
        Negocio negocio = negocioMapper.toModel(negocioRequest);

        //despues lo paso a entidad
        NegocioEntidad negocioEntidad= negocioMapper.toEntidad(negocio);

        //y ya despues lo guardo y lo que me retorna .save, lo mappeo
        return negocioMapper.toModel(negocioRepositorio.save(negocioEntidad));
    }



}

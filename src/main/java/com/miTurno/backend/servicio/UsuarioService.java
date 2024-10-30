package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.DetallesNegocioRequest;
import com.miTurno.backend.DTO.ProfesionalRequest;
import com.miTurno.backend.DTO.UsuarioRequest;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.modelo.DetallesNegocio;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final RolRepositorio rolRepositorio;
    private final DetallesNegocioService detallesNegocioService;
    private final ProfesionalesXNegocioService profesionalesXNegocioService;


    @Autowired
    public UsuarioService(UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper, RolRepositorio rolRepositorio, DetallesNegocioService detallesNegocioService, ProfesionalesXNegocioService profesionalesXNegocioService) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.rolRepositorio = rolRepositorio;
        this.detallesNegocioService = detallesNegocioService;
        this.profesionalesXNegocioService = profesionalesXNegocioService;
    }

    //GET
    public List<UsuarioEntidad> obtenerTodosLosUsuarios(){
        return usuarioRepositorio.findAll();
    }
    public UsuarioEntidad buscarUsuario(Long id) throws UsuarioNoExistenteException{

        return usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));
    }
    public UsuarioEntidad obtenerUsuariosByEmailAndPassword(String email,String password)throws UsuarioNoExistenteException{
        return usuarioRepositorio.findByEmailAndPassword(email,password).orElseThrow(()-> new EmailNoExistenteException(email));
    }

    public List<UsuarioEntidad> obtenerUsuariosPorRol(RolUsuarioEnum rol) {
        return usuarioRepositorio.findByRolEntidad_Rol(rol);
    }
    
    public List<UsuarioEntidad> obtenerUsuariosPorEstado(Boolean estado) {
        return usuarioRepositorio.findByEstado(estado);
    }

    public List<UsuarioEntidad> obtenerUsuariosPorRolYEstado(RolUsuarioEnum rol, Boolean estado){
        return usuarioRepositorio.findByRolEntidad_RolAndEstado(rol, estado);
    }

    //POST usuario
    public Usuario crearUnUsuario(UsuarioRequest usuario) throws EmailYaExisteException, CelularYaExisteException {

        //verificar si ya existe un mail, si no existe, tira excepcion

        if (usuarioRepositorio.existsByEmail(usuario.getEmail())){
            throw new EmailYaExisteException(usuario.getEmail());
        }

        if (usuarioRepositorio.existsByTelefono(usuario.getTelefono())){
            throw new CelularYaExisteException(usuario.getTelefono());
        }


        UsuarioEntidad usuarioEntidad= usuarioMapper.toEntidad(usuarioMapper.toModel(usuario));
        usuarioEntidad = usuarioRepositorio.save(usuarioEntidad);
        return usuarioMapper.toModel(usuarioEntidad);
    }
    //POST negocio
    public DetallesNegocio crearUnNegocio(DetallesNegocioRequest detallesNegocioRequest)
            throws NombreNegocioYaExisteException, RolIncorrectoException {

        String nombreNegocio = detallesNegocioRequest.getNombre();
        RolUsuarioEnum rolUsuarioEnum = detallesNegocioRequest.getRolEntidad();

        if (rolUsuarioEnum != RolUsuarioEnum.NEGOCIO) {
            throw new RolIncorrectoException(RolUsuarioEnum.NEGOCIO, rolUsuarioEnum);
        }

        if (usuarioRepositorio.existsByNombreAndRolEntidad_Rol(nombreNegocio, rolUsuarioEnum)) {
            throw new NombreNegocioYaExisteException(nombreNegocio);
        }

        // Crear RolEntidad usando el RolUsuarioEnum
        RolEntidad rolEntidad = new RolEntidad();
        rolEntidad.setRol(rolUsuarioEnum);

        // Crear UsuarioRequest usando RolEntidad
        UsuarioRequest usuarioRequest = UsuarioRequest.builder()
                .rolEntidad(rolEntidad.getRol())
                .nombre(detallesNegocioRequest.getNombre())
                .apellido(detallesNegocioRequest.getApellido())
                .email(detallesNegocioRequest.getEmail())
                .password(detallesNegocioRequest.getPassword())
                .telefono(detallesNegocioRequest.getTelefono())
                .fechaNacimiento(detallesNegocioRequest.getFechaNacimiento())
                .build();

        // Crear el usuario
        crearUnUsuario(usuarioRequest);

        // Crear los detalles del negocio
        return detallesNegocioService.crearDetallesNegocio(detallesNegocioRequest);
    }

    //POST profesional

    public Usuario crearUnprofesional(ProfesionalRequest profesionalRequest) throws  RolIncorrectoException {

        RolUsuarioEnum rolUsuarioEnum = profesionalRequest.getRolEntidad();

        if (rolUsuarioEnum != RolUsuarioEnum.PROFESIONAL) {
            throw new RolIncorrectoException(RolUsuarioEnum.PROFESIONAL, rolUsuarioEnum);
        }

        // Crear RolEntidad usando el RolUsuarioEnum
        RolEntidad rolEntidad = new RolEntidad();
        rolEntidad.setRol(rolUsuarioEnum);

        // Crear UsuarioRequest usando RolEntidad
        UsuarioRequest usuarioRequest = UsuarioRequest.builder()
                .rolEntidad(rolEntidad.getRol())
                .nombre(profesionalRequest.getNombre())
                .apellido(profesionalRequest.getApellido())
                .email(profesionalRequest.getEmail())
                .password(profesionalRequest.getPassword())
                .telefono(profesionalRequest.getTelefono())
                .fechaNacimiento(profesionalRequest.getFechaNacimiento())
                .build();

        // Crear el usuario
        Usuario profesional = crearUnUsuario(usuarioRequest);


        //el id relacion lo agarro de onda
        Long idRelacion =profesionalesXNegocioService.crearProfesionalPorNegocio(profesional.getIdUsuario(), profesionalRequest.getIdNegocio());


        // Crear los detalles del negocio
        return profesional;
    }


    //obtener todos los negocios

    public List<UsuarioEntidad> obtenerTodosLosNegocios(){
        return usuarioRepositorio.findByRolEntidad_Rol(RolUsuarioEnum.NEGOCIO);
    }

    //obtener negocio x nombre
    public UsuarioEntidad obtenerNegocioPorNombre(String nombre) throws NombreNoExisteException{
       return usuarioRepositorio.findByRolEntidad_RolAndNombre(RolUsuarioEnum.NEGOCIO,nombre).orElseThrow(()->new NombreNoExisteException(nombre));
    }

    //obtener negocio x id
    public UsuarioEntidad obtenerNegocioPorId(Long idNegocio) throws UsuarioNoExistenteException{
        return usuarioRepositorio.findByRolEntidad_RolAndIdUsuario(RolUsuarioEnum.NEGOCIO, idNegocio).orElseThrow(()->new UsuarioNoExistenteException(idNegocio));
    }

    //UPDATE
    public Usuario actualizarUsuarioPorId(Long id, Usuario actualizado) throws UsuarioNoExistenteException {
        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));

        //si existe
        usuarioEntidad.setNombre(actualizado.getNombre());
        usuarioEntidad.setApellido(actualizado.getApellido());
        usuarioEntidad.setEmail(actualizado.getEmail());
        usuarioEntidad.setPassword((actualizado.getPassword()));
        usuarioEntidad.setTelefono(actualizado.getTelefono());



        usuarioEntidad.setRolEntidad(rolRepositorio.findByRol(actualizado.getRolUsuario()));
        // la fecha de nacimiento y el id no se podrian modificar
        usuarioEntidad= usuarioRepositorio.save(usuarioEntidad);
        return usuarioMapper.toModel(usuarioEntidad);
    }

    //DELETE
    public Boolean eliminarUsuarioPorId(Long id){
        Boolean rta = false;//
        if(usuarioRepositorio.existsById(id)){
           UsuarioEntidad usuarioEntidad= usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));

            usuarioEntidad.setEstado(false);
            usuarioRepositorio.save(usuarioEntidad);
            rta = true;
        }
        return rta;
    }

}

package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.CredencialesEntidad;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.repositorio.CredencialesRepositorio;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.request.UsuarioRequest;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.DTO.Negocio;
import com.miTurno.backend.DTO.Usuario;
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
   // private final NegocioService negocioService;

    private final CredencialesRepositorio credencialesRepositorio;


    @Autowired
    public UsuarioService(UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper, RolRepositorio rolRepositorio, CredencialesRepositorio credencialesRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.rolRepositorio = rolRepositorio;
        //this.negocioService = negocioService;

        this.credencialesRepositorio = credencialesRepositorio;
    }

    //get
    public List<UsuarioEntidad> obtenerTodosLosUsuarios(){
        return usuarioRepositorio.findAll();
    }

    //get x id
    public Usuario buscarUsuario(Long id) throws UsuarioNoExistenteException{

        return usuarioMapper.toModel(usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id)));
    }

    //get x email y contra
    /*public UsuarioEntidad obtenerUsuariosByEmailAndPassword(String email,String password)throws UsuarioNoExistenteException{
        return usuarioRepositorio.findByEmailAndPassword(email,password).orElseThrow(()-> new EmailNoExistenteException(email));
    }

    //get x rol
    public List<UsuarioEntidad> obtenerUsuariosPorRol(RolUsuarioEnum rol) {
        return usuarioRepositorio.findByRolEntidad_Rol(rol);
    }

    //get x estado
    public List<UsuarioEntidad> obtenerUsuariosPorEstado(Boolean estado) {
        return usuarioRepositorio.findByEstado(estado);
    }

    public List<UsuarioEntidad> obtenerUsuariosPorRolYEstado(RolUsuarioEnum rol, Boolean estado){
        return usuarioRepositorio.findByRolEntidad_RolAndEstado(rol, estado);
    }
*/
    //POST usuario
  /*  public Usuario crearUnUsuario(Usuario usuario) throws EmailYaExisteException, CelularYaExisteException {

        //verificamos en el repo de credenciales
        //verificar si ya existe un mail, si es asi tira excepcion
        if (credencialesRepositorio.findByEmail(usuario.getEmail()).isPresent()){
            throw new EmailYaExisteException(usuario.getEmail());
        }

        //verificar si ya existe un celular, si es asi tira excepcion
        if (usuarioRepositorio.findByTelefono(usuario.getTelefono()).isPresent()){
            throw new CelularYaExisteException(usuario.getTelefono());
        }

        UsuarioEntidad usuarioEntidad= usuarioMapper.toEntidad(usuario);

        usuarioEntidad = usuarioRepositorio.save(usuarioEntidad);
        return usuarioMapper.toModel(usuarioEntidad);
    }
*/

    //GET usuario x id y rol
   /* public Usuario obtenerUsuarioPorIdyRol(RolUsuarioEnum rolUsuarioEnum,Long idNegocio){
        CredencialesEntidad credencialesEntidad= credencialesRepositorio.findByIdAndRolEntidad_Rol(idNegocio,rolUsuarioEnum).orElseThrow(()->new UsuarioNoExistenteException(idNegocio));

        return usuarioMapper.toModel(credencialesEntidad.getUsuario());

    }
*/
    //UPDATE
    public Usuario actualizarUsuarioPorId(Long id, Usuario actualizado) throws UsuarioNoExistenteException {

        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));

        //si existe
        usuarioEntidad.setNombre(actualizado.getNombre());
        usuarioEntidad.setApellido(actualizado.getApellido());


        //  Actualizar credenciales
        //    actualizarCredenciales(usuarioEntidad, actualizado);
        usuarioEntidad.getCredenciales().setEmail(actualizado.getEmail());
        usuarioEntidad.getCredenciales().setPassword((actualizado.getPassword()));
        usuarioEntidad.getCredenciales().setTelefono(actualizado.getTelefono());


        //usuarioEntidad.getCredenciales().setRolEntidad(rolRepositorio.findByRol(actualizado.getRolUsuario()));
        // la fecha de nacimiento, el rol y el id no se podrian modificar

        usuarioEntidad= usuarioRepositorio.save(usuarioEntidad);

        return usuarioMapper.toModel(usuarioEntidad);
    }

    //DELETE
    public Boolean eliminarUsuarioPorId(Long id){
        Boolean rta = false;//
        if(usuarioRepositorio.existsById(id)){
           UsuarioEntidad usuarioEntidad= usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));

            usuarioEntidad.getCredenciales().setEstado(false);

            usuarioRepositorio.save(usuarioEntidad);
            rta = true;
        }
        return rta;
    }

}

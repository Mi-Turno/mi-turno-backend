package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.repositorio.CredencialesRepositorio;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.model.Usuario;
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


    private final CredencialesRepositorio credencialesRepositorio;
    private final RolRepositorio rolRepositorio;


    @Autowired
    public UsuarioService(UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper, CredencialesRepositorio credencialesRepositorio, RolRepositorio rolRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.credencialesRepositorio = credencialesRepositorio;
        this.rolRepositorio = rolRepositorio;
    }

    //get
    public List<Usuario> obtenerTodosLosUsuarios(){
        return usuarioMapper.toModelList(usuarioRepositorio.findAll());
    }

    //get x id
    public Usuario buscarUsuario(Long id) throws UsuarioNoExistenteException{

        return usuarioMapper.toModel(usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id)));
    }

    //get x email y contra
   public Usuario obtenerUsuariosByEmailAndPassword(String email,String password)throws UsuarioNoExistenteException{
        return usuarioMapper.toModel(usuarioRepositorio.findByCredencialEmailAndCredencialPassword(email,password));
   }

    //get x rol
    public List<Usuario> obtenerUsuariosPorRol(RolUsuarioEnum rol) {

        List<UsuarioEntidad> listadoDeUsuariosEntidad = usuarioRepositorio.findAllByRolEntidad_Rol(rol);

        return usuarioMapper.toModelList(listadoDeUsuariosEntidad);
    }

    //get x estado
//    public List<UsuarioEntidad> obtenerUsuariosPorEstado(Boolean estado) {
//        return usuarioRepositorio.findByEstado(estado);
//    }
//
//    public List<UsuarioEntidad> obtenerUsuariosPorRolYEstado(RolUsuarioEnum rol, Boolean estado){
//        return usuarioRepositorio.findByRolEntidad_RolAndEstado(rol, estado);
//    }

    //POST usuario
    public Usuario crearUnUsuario(Usuario usuario) throws EmailYaExisteException, TelefonoYaExisteException {

        //verificamos en el repo de credenciales
        //verificar si ya existe un mail, si es asi tira excepcion
        if (credencialesRepositorio.findByEmail(usuario.getCredencial().getEmail()).isPresent()){
            throw new EmailYaExisteException(usuario.getCredencial().getEmail());
        }

        //verificar si ya existe un celular, si es asi tira excepcion
        if (credencialesRepositorio.findByTelefono(usuario.getCredencial().getTelefono()).isPresent()){
            throw new TelefonoYaExisteException(usuario.getCredencial().getTelefono());
        }

        //setteamos el estado del usuario en true
        usuario.getCredencial().setEstado(true);

        RolEntidad rolEntidad = rolRepositorio.findByRol(usuario.getRolUsuario());

        UsuarioEntidad usuarioEntidad= usuarioMapper.toEntidad(usuario,rolEntidad);

        usuarioEntidad = usuarioRepositorio.save(usuarioEntidad);
        return usuarioMapper.toModel(usuarioEntidad);
    }


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
        usuarioEntidad.getCredencial().setEmail(actualizado.getCredencial().getEmail());
        usuarioEntidad.getCredencial().setPassword((actualizado.getCredencial().getPassword()));
        usuarioEntidad.getCredencial().setTelefono(actualizado.getCredencial().getTelefono());


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

            usuarioEntidad.getCredencial().setEstado(false);

            usuarioRepositorio.save(usuarioEntidad);
            rta = true;
        }
        return rta;
    }

}

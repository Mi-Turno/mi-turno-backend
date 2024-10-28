package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.CelularYaExisteException;
import com.miTurno.backend.excepcion.EmailNoExistenteException;
import com.miTurno.backend.excepcion.EmailYaExisteException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.mapper.UsuarioMapper;
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

    @Autowired
    public UsuarioService(UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper, RolRepositorio rolRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.rolRepositorio = rolRepositorio;
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

    //POST
    public Usuario crearUnUsuario(Usuario usuario) throws EmailYaExisteException, CelularYaExisteException {

        //verificar si ya existe un mail, si no existe, tira excepcion

        if (usuarioRepositorio.existsByEmail(usuario.getEmail())){
            throw new EmailYaExisteException(usuario.getEmail());
        }

        if (usuarioRepositorio.existsByTelefono(usuario.getTelefono())){
            throw new CelularYaExisteException(usuario.getTelefono());
        }


        UsuarioEntidad usuarioEntidad= usuarioMapper.toEntidad(usuario);
        usuarioEntidad = usuarioRepositorio.save(usuarioEntidad);
        return usuarioMapper.toModel(usuarioEntidad);
    }

//    public Usuario convertirEntidadAUsuario(UsuarioEntidad usuarioEntidad){
//        Usuario usuario = new Usuario();
//        usuario.setIdUsuario(usuarioEntidad.getIdUsuario());
//        usuario.setNombre(usuarioEntidad.getNombre());
//        usuario.setApellido(usuarioEntidad.getApellido());
//        usuario.setCorreoElectronico(usuarioEntidad.getCorreoElectronico());
//        usuario.setPassword(usuarioEntidad.getPassword());
//        usuario.setCelular(usuarioEntidad.getCelular());
//        usuario.setFechaNacimiento(usuarioEntidad.getFechaNacimiento());
//        usuario.setRolUsuario(usuarioEntidad.getRolUsuarioEnum());
//
//        return usuario;
//    }
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

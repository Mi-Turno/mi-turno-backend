package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    //GET
    public List<UsuarioEntidad> obtenerTodosLosUsuarios(){
        return usuarioRepositorio.findAll();
    }
    public UsuarioEntidad buscarUsuario(Long id) throws UsuarioNoExistenteException{

        return usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));
    }
    //POST
    public Usuario crearUnUsuario(Usuario usuario){
    UsuarioEntidad usuarioEntidad = new UsuarioEntidad();

        usuarioEntidad.setNombre(usuario.getNombre());
        usuarioEntidad.setApellido(usuario.getApellido());
        usuarioEntidad.setCorreoElectronico(usuario.getCorreoElectronico());
        usuarioEntidad.setCelular(usuario.getCelular());
        usuarioEntidad.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioEntidad.setRolUsuarioEnum(usuario.getRolUsuario());

        usuarioEntidad = usuarioRepositorio.save(usuarioEntidad);
        return convertirEntidadAUsuario(usuarioEntidad);
    }
    public Usuario convertirEntidadAUsuario(UsuarioEntidad usuarioEntidad){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioEntidad.getIdUsuario());
        usuario.setNombre(usuarioEntidad.getNombre());
        usuario.setApellido(usuarioEntidad.getApellido());
        usuario.setCorreoElectronico(usuarioEntidad.getCorreoElectronico());
        usuario.setCelular(usuarioEntidad.getCelular());
        usuario.setFechaNacimiento(usuarioEntidad.getFechaNacimiento());
        usuario.setRolUsuario(usuarioEntidad.getRolUsuarioEnum());

        return usuario;
    }
    //UPDATE
    public Usuario actualizarUsuarioPorId(Long id, Usuario actualizado) throws UsuarioNoExistenteException {
        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(id).orElseThrow(()-> new UsuarioNoExistenteException(id));
        usuarioEntidad.setNombre(actualizado.getNombre());
        usuarioEntidad.setApellido(actualizado.getApellido());
        usuarioEntidad.setCorreoElectronico(actualizado.getCorreoElectronico());
        usuarioEntidad.setCelular(actualizado.getCelular());
        usuarioEntidad.setRolUsuarioEnum(actualizado.getRolUsuario());
        // la fecha de nacimiento y el id no se podrian modificar
        usuarioEntidad= usuarioRepositorio.save(usuarioEntidad);
        return convertirEntidadAUsuario(usuarioEntidad);
    }

    //DELETE
    public boolean eliminarUsuarioPorId(Long id){
        boolean rta = false;
        if(usuarioRepositorio.existsById(id)){
            usuarioRepositorio.deleteById(id);
            rta = true;
        }
        return rta;
    }

}

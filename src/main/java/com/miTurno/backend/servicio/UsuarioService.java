package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.UsuarioEntidad;
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
    public Optional<UsuarioEntidad> buscarUsuario(Long id){
        return usuarioRepositorio.findById(id);
    }
    //POST
    public Usuario crearUnUsuario(Usuario usuario){
    UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
    usuarioEntidad.setIdUsuario(usuario.getIdUsuario());
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

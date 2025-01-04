package com.miTurno.backend.servicio;

import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.data.repositorio.CredencialesRepositorio;
import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.mapper.UsuarioMapper;
import com.miTurno.backend.data.dtos.response.Usuario;
import com.miTurno.backend.data.repositorio.RolRepositorio;
import com.miTurno.backend.data.repositorio.UsuarioRepositorio;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;


    private final CredencialesRepositorio credencialesRepositorio;
    private final RolRepositorio rolRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;


    @Autowired
    public UsuarioService(UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper, CredencialesRepositorio credencialesRepositorio, RolRepositorio rolRepositorio, PasswordEncoder passwordEncoder, AuthService authService) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.credencialesRepositorio = credencialesRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    //get
    public List<Usuario> obtenerTodosLosUsuarios(){
        return usuarioMapper.toModelList(usuarioRepositorio.findAll());
    }

    //get x id
    public Usuario buscarUsuario(Long id) throws EntityNotFoundException{

        return usuarioMapper.toModel(usuarioRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Usuario con id: "+id +" no encontrado.")));
    }

    //get x email y contra
//   public Usuario obtenerUsuariosByEmailAndPassword(String email,String password)throws RecursoNoExisteException{
//
//        //email no existe
//        if (!usuarioRepositorio.existsByCredencialEmail(email)){
//            throw new EmailNoExistenteException(email);
//        }
//
//        //email existe pero contrasenia incorrecta
//
//        UsuarioEntidad usuarioEntidad= usuarioRepositorio.findByCredencialEmailAndCredencialPassword(email,password)
//                .orElseThrow(()-> new ContraseniaIncorrectaException(password));
//
//        return usuarioMapper.toModel(usuarioEntidad);
//   }

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

    //POST usuario todo NO SE USA
    public Usuario crearUnUsuario(Usuario usuario) throws EntityExistsException {

        //verificamos en el repo de credenciales
        //verificar si ya existe un mail, si es asi tira excepcion
        if (credencialesRepositorio.findByEmail(usuario.getCredencial().getEmail()).isPresent()){
            throw new EntityExistsException("El cliente con el email: "+usuario.getCredencial().getEmail()+" ya existe.");
        }

        //verificar si ya existe un celular, si es asi tira excepcion
        if (credencialesRepositorio.findByTelefono(usuario.getCredencial().getTelefono()).isPresent()){
            throw new EntityExistsException("El cliente con el telefono: "+usuario.getCredencial().getTelefono()+" ya existe.");
        }
        //encriptamos la password
        usuario.getCredencial().setPassword(passwordEncoder.encode(usuario.getCredencial().getPassword()));

        RolEntidad rolEntidad = rolRepositorio.findByRol(usuario.getRolUsuario());

        //setteamos el estado del usuario en false, debido a que no va estar verificado
        usuario.getCredencial().setEstado(false);



        UsuarioEntidad usuarioEntidad= usuarioMapper.toEntidad(usuario,rolEntidad);

        return usuarioMapper.toModel(usuarioRepositorio.save(usuarioEntidad));
    }


    //GET usuario x id y rol
   /* public Usuario obtenerUsuarioPorIdyRol(RolUsuarioEnum rolUsuarioEnum,Long idNegocio){
        CredencialesEntidad credencialesEntidad= credencialesRepositorio.findByIdAndRolEntidad_Rol(idNegocio,rolUsuarioEnum)
        .orElseThrow(()->new UsuarioNoExistenteException(idNegocio));

        return usuarioMapper.toModel(credencialesEntidad.getUsuario());

    }
*/
    //UPDATE
    public Usuario actualizarUsuarioPorId(Long id, Usuario actualizado) throws EntityNotFoundException {

        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Usuario con id: "+id +" no encontrado."));

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
    public Boolean eliminarUsuarioPorId(Long id) throws EntityNotFoundException{
        Boolean rta = false;//
        if(usuarioRepositorio.existsById(id)){
           UsuarioEntidad usuarioEntidad= usuarioRepositorio.findById(id)
                   .orElseThrow(()-> new EntityNotFoundException("Usuario con id: "+id +" no encontrado."));

            usuarioEntidad.getCredencial().setEstado(false);

            usuarioRepositorio.save(usuarioEntidad);
            rta = true;
        }
        return rta;
    }

}

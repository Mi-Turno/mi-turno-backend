package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad,Long> {




    //

   //

  //

  //  Optional<UsuarioEntidad> findByRolEntidad_RolAndIdUsuario(RolUsuarioEnum rolEntidad_rol, Long idUsuario);

  //  Optional<UsuarioEntidad> findByRolEntidad_RolAndNombre(RolUsuarioEnum rolEntidad_rol, String nombre);







    /**uso Optional para poder lanzar la exception ya que es un email*/
    UsuarioEntidad findByCredencialesEmailAndCredencialesPassword(String email, String password);

}

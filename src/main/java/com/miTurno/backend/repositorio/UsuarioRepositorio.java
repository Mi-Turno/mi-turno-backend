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




    Optional<UsuarioEntidad> findByTelefono(String telefono);

    boolean existsByTelefonoAndEmail(@Email String email, String telefono);

    boolean existsByNombreAndRolEntidad_Rol(String nombreNegocio, RolUsuarioEnum rolUsuarioEnum);

    Optional<UsuarioEntidad> findByRolEntidad_RolAndIdUsuario(RolUsuarioEnum rolEntidad_rol, Long idUsuario);

    Optional<UsuarioEntidad> findByRolEntidad_RolAndNombre(RolUsuarioEnum rolEntidad_rol, String nombre);

    List<UsuarioEntidad> findByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);

    List<UsuarioEntidad> findByEstado(Boolean estado);

    List <UsuarioEntidad> findByRolEntidad_RolAndEstado (RolUsuarioEnum rol, Boolean estado);

    /**uso Optional para poder lanzar la exception ya que es un email*/
    Optional<UsuarioEntidad> findByEmailAndPassword(@Email String email, String password);

}

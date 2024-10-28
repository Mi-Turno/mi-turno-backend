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


    boolean existsByEmail(@Email String email);
    boolean existsByTelefono(String telefono);
    boolean existsByTelefonoAndEmail(@Email String email, String telefono);
    List<UsuarioEntidad> findByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);
    /**uso Optional para poder lanzar la exception ya que es un email*/
    Optional<UsuarioEntidad> findByEmailAndPassword(@Email String email, String password);

}

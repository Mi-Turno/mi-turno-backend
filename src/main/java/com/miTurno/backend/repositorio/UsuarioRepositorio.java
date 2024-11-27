package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.CredencialEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad,Long> {


    Optional<UsuarioEntidad> findByCredencialEmailAndCredencialPassword(@Email String email, String password);
    List<UsuarioEntidad> findAllByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);



    Boolean existsByCredencialEmail(@Email String email);
    Optional<UsuarioEntidad> findByCredencialEmail(@Email String email);
}

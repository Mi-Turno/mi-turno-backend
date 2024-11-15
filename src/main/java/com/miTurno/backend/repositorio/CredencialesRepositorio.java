package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.CredencialEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CredencialesRepositorio extends JpaRepository<CredencialEntidad,Long> {

    Optional<CredencialEntidad> findByTelefono(String telefono);
    Optional<CredencialEntidad>findByEmail(String email);
    List<CredencialEntidad>findAllByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);

}

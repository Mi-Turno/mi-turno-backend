package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.CredencialEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredencialesRepositorio extends JpaRepository<CredencialEntidad,Long> {

    Optional<CredencialEntidad> findByTelefono(String telefono);
    Optional<CredencialEntidad>findByEmail(String email);
//    List<CredencialEntidad>findAllByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);
    Optional<CredencialEntidad>findCredencialEntidadByCodigo(String codigo);


}

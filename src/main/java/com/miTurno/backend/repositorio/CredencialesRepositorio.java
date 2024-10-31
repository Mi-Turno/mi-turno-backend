package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.CredencialesEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CredencialesRepositorio extends JpaRepository<CredencialesEntidad,Long> {

    Optional<CredencialesEntidad>findByEmail(String email);

    //Optional<CredencialesEntidad>findByIdAndRolEntidad_Rol(Long idNegocio, RolUsuarioEnum rolUsuarioEnum);

    List<CredencialesEntidad>findAllByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);

}

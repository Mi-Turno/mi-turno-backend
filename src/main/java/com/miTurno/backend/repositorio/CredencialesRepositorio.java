package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.CredencialesEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CredencialesRepositorio extends JpaRepository<CredencialesEntidad,Long> {

    Optional<CredencialesEntidad> findByTelefono(String telefono);
    Optional<CredencialesEntidad>findByEmail(String email);
    List<CredencialesEntidad>findAllByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);

}

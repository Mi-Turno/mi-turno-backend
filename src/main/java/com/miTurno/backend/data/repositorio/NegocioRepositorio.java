package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.NegocioEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NegocioRepositorio extends JpaRepository<NegocioEntidad,Long> {
    NegocioEntidad getNegocioEntidadById(Long idNegocio);

    Optional<NegocioEntidad> getNegocioEntidadByNombreIgnoreCase(String nombreNegocio);

    List<NegocioEntidad> getNegocioEntidadsByNombreLikeIgnoreCase(String nombreNegocio);

    boolean existsByNombreAndRolEntidad_Rol(String nombreNegocio, RolUsuarioEnum rolUsuarioEnum);

    boolean existsByCredencial_Telefono(String telefono);

    boolean existsByCredencial_Email(@Email String email);

}

package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NegocioRepositorio extends JpaRepository<NegocioEntidad,Long> {
    NegocioEntidad getNegocioEntidadById(Long idNegocio);

    Optional<NegocioEntidad> getNegocioEntidadByNombreIgnoreCase(String nombreNegocio);

    List<NegocioEntidad> getNegocioEntidadsByNombreLikeIgnoreCase(String nombreNegocio);

    boolean existsByNombreAndCredencialesRolEntidad(String nombreNegocio,RolUsuarioEnum rolUsuarioEnum);

    boolean existsByCredenciales_Telefono(String telefono);

    boolean existsByCredenciales_Email(@Email String email);

}

package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NegocioRepositorio extends JpaRepository<NegocioEntidad,Long> {
    NegocioEntidad getNegocioEntidadByIdUsuario(Long idNegocio);
    Optional<NegocioEntidad> getNegocioEntidadByNombreIgnoreCase(String nombreNegocio);
    List<NegocioEntidad> getNegocioEntidadsByNombreLikeIgnoreCase(String nombreNegocio);

}

package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.ServicioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicioRepositorio extends JpaRepository<ServicioEntidad, Long> {

    List<ServicioEntidad> findAllByNegocioEntidadId(Long idNegocio);

    ServicioEntidad findByNegocioEntidadIdAndId(Long idNegocio,Long idServicio);

    Optional<ServicioEntidad> findByIdAndNegocioEntidadId(Long idServicio, Long idNegocio);

    List<ServicioEntidad> findByNegocioEntidadIdAndEstado(Long idNegocio, Boolean estado);

    ServicioEntidad getServicioEntidadByNegocioEntidadIdAndId(Long idNegocio, Long idServicio);
}

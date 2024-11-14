package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.ServicioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepositorio extends JpaRepository<ServicioEntidad, Long> {

    List<ServicioEntidad> findAllByNegocioEntidadId(Long idNegocio);

    ServicioEntidad findByNegocioEntidadIdAndId(Long idNegocio,Long idServicio);

    ServicioEntidad findByIdAndNegocioEntidadId(Long idServicio, Long idNegocio);

    List<ServicioEntidad> findByNegocioEntidadIdAndEstado(Long idNegocio, Boolean estado);

    ServicioEntidad getServicioEntidadByNegocioEntidadIdAndId(Long idNegocio, Long idServicio);
}

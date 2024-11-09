package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepositorio extends JpaRepository<ServicioEntidad, Long> {

    List<ServicioEntidad> findAllByNegocioEntidad_IdUsuario(Long idNegocio);
    ServicioEntidad findByNegocioEntidad_IdUsuarioAndIdServicio(Long idNegocio,Long idServicio);

    ServicioEntidad findByIdServicioAndNegocioEntidad_IdUsuario(Long idServicio, Long idNegocio);

    List<ServicioEntidad> findByNegocioEntidad_IdUsuarioAndEstado(Long idNegocio, Boolean estado);

    ServicioEntidad getServicioEntidadByNegocioEntidad_IdUsuarioAndIdServicio(Long idNegocio,Long idServicio);
}

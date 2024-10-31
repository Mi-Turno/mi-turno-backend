package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.ServicioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepositorio extends JpaRepository<ServicioEntidad, Long> {
   /* List<ServicioEntidad> findAllByIdNegocio(Long idNegocio);
    ServicioEntidad findByIdNegocioAndIdServicio(Long idNegocio,Long idServicio);*/
}

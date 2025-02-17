package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.TurnoEntidad;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnoRepositorio extends JpaRepository<TurnoEntidad,Long> {

   List<TurnoEntidad> findAllByNegocioEntidadId(Long idNegocio);
   TurnoEntidad findByNegocioEntidadIdAndId(Long idNegocio, Long idTurno);

}

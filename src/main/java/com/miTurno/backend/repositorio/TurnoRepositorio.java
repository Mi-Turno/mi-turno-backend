package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.TurnoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnoRepositorio extends JpaRepository<TurnoEntidad,Long> {
   /* List<TurnoEntidad> findByNegocioEntidad_Id(Long idNegocio);
    TurnoEntidad findByNegocioEntidad_IdAndTurnoId(Long idNegocio,Long idTurno);*/
   List<TurnoEntidad>findAllByNegocioEntidad_IdUsuario(Long idNegocio);

}

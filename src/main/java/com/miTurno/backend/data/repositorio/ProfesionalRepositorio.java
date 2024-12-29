package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.ProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesionalRepositorio extends JpaRepository<ProfesionalEntidad,Long> {

    List<ProfesionalEntidad> findAllByNegocioEntidadIdAndCredencial_Estado(Long idNegocio, Boolean estado);

    //todo hacerlo optional
    ProfesionalEntidad findByIdAndNegocioEntidadId(Long idProfesional,Long idNegocio);

}

package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesionalRepositorio extends JpaRepository<ProfesionalEntidad,Long> {

    List<ProfesionalEntidad> findAllByNegocioEntidadIdAndCredenciales_Estado(Long idNegocio, Boolean estado);

    ProfesionalEntidad findByIdAndNegocioEntidadId(Long idProfesional,Long idNegocio);

}

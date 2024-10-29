package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.HorarioXProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioXProfesionalRepositorio extends JpaRepository<HorarioXProfesionalEntidad,Long> {
    HorarioXProfesionalEntidad findProfesionalXHorarioByidProfesional(Long idProfesional);
}

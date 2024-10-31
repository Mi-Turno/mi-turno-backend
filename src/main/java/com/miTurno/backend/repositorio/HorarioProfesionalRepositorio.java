package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioProfesionalRepositorio extends JpaRepository<HorarioProfesionalEntidad,Long> {
}

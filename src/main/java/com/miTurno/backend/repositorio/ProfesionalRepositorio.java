package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.ProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesionalRepositorio extends JpaRepository<ProfesionalEntidad,Long> {
}

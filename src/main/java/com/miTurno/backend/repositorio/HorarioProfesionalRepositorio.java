package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.tipos.DiasEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioProfesionalRepositorio extends JpaRepository<HorarioProfesionalEntidad,Long> {

    List<HorarioProfesionalEntidad> findByProfesionalEntidadId(Long idProfesional);

    List<HorarioProfesionalEntidad> findByProfesionalEntidadIdAndDiaEntidad(Long idProfesional, DiasEnum diaEnum);
}

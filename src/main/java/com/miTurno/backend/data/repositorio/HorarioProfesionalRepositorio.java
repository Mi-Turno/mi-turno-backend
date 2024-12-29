package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.HorarioProfesionalEntidad;
import com.miTurno.backend.tipos.DiasEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioProfesionalRepositorio extends JpaRepository<HorarioProfesionalEntidad,Long> {

    List<HorarioProfesionalEntidad> findByProfesionalEntidadId(Long idProfesional);

    List<HorarioProfesionalEntidad> findByProfesionalEntidadIdAndDiaEntidad_Dia(Long idProfesional, DiasEnum diaEnum);


}

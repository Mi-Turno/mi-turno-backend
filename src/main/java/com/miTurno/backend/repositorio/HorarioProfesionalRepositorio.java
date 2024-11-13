package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.tipos.DiasEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioProfesionalRepositorio extends JpaRepository<HorarioProfesionalEntidad,Long> {
    // Obtener horarios por ID del profesional
    List<HorarioProfesionalEntidad> findByProfesionalEntidad_IdUsuario(Long idProfesional);

    List<HorarioProfesionalEntidad> findByProfesionalEntidad_IdUsuarioAndDiaEntidad_Dia(Long idProfesional, DiasEnum diaEnum);
}

package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.tipos.DiasEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioProfesionalRepositorio extends JpaRepository<HorarioProfesionalEntidad,Long> {
    // Obtener horarios por ID del profesional
    List<HorarioProfesionalEntidad> findByProfesionalEntidad_IdUsuario(Long idProfesional);

    // Obtener horarios por nombre del día
//    List<HorarioProfesionalEntidad> findByDiaEntidad_Nombre(DiasEnum nombreDia);

    // Obtener horarios por ID del profesional y nombre del día
    List<HorarioProfesionalEntidad> findByProfesionalEntidad_IdUsuarioAndDiaEntidad_Dia(Long idProfesional, DiasEnum diaEnum);
}

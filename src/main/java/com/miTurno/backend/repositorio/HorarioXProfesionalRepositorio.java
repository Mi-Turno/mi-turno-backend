package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.HorarioXProfesionalEntidad;
import com.miTurno.backend.tipos.DiasEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioXProfesionalRepositorio extends JpaRepository<HorarioXProfesionalEntidad,Long> {
    HorarioXProfesionalEntidad findProfesionalXHorarioByidProfesional(Long idProfesional);

    List<HorarioXProfesionalEntidad> findByIdProfesionalAndDiaEntidad_Dia(Long idProfesional, DiasEnum dia);

}

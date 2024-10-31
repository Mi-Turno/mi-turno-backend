package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.ProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesionalRepositorio extends JpaRepository<ProfesionalEntidad,Long> {
    //List<ProfesionalEntidad> findAllByid_negocio(Long idNegocio);
}

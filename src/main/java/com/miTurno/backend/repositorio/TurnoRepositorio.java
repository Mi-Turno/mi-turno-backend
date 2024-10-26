package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.TurnoEntidad;
import com.miTurno.backend.modelo.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<TurnoEntidad,Long> {

}

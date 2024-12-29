package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.EstadoTurnoEntidad;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoTurnoRepositorio extends JpaRepository<EstadoTurnoEntidad,Long> {
    EstadoTurnoEntidad findByEstadoTurno(EstadoTurnoEnum estado);
}

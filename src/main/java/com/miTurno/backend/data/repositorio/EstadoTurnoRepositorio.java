package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.EstadoTurnoEntidad;
import com.miTurno.backend.data.domain.TurnoEntidad;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoTurnoRepositorio extends JpaRepository<EstadoTurnoEntidad,Long> {
    EstadoTurnoEntidad findByEstadoTurno(EstadoTurnoEnum estado);
    //List<TurnoEntidad> findByEstadoTurno(EstadoTurnoEnum estadoTurnoEnum);
}

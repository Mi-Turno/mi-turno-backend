package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallesNegocioRepositorio extends JpaRepository<DetallesNegocioEntidad,Long> {

}

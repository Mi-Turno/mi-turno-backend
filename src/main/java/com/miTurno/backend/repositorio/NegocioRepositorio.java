package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.NegocioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegocioRepositorio extends JpaRepository<NegocioEntidad,Long> {

}

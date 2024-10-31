package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.NegocioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NegocioRepositorio extends JpaRepository<NegocioEntidad,Long> {

}

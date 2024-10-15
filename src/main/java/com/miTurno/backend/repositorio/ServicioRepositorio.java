package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.ServicioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepositorio extends JpaRepository<ServicioEntidad, Long> {

}

package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.CredencialesEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredencialesRepositorio extends JpaRepository<CredencialesEntidad,Long> {

    Optional<CredencialesEntidad>findByEmail(String email);

}

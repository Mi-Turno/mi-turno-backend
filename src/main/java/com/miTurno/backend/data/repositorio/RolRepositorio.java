package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepositorio extends JpaRepository<RolEntidad,Long> {
    RolEntidad findByRol(RolUsuarioEnum rolUsuarioEnum);
}

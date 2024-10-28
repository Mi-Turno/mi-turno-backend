package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepositorio extends JpaRepository<RolEntidad,Long> {
    RolEntidad findByRol(RolUsuarioEnum rolUsuarioEnum);
}

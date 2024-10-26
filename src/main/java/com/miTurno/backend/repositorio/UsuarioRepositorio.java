package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad,Long> {

    List<UsuarioEntidad> findByRolUsuarioEnum(RolUsuarioEnum rolUsuarioEnum);
}

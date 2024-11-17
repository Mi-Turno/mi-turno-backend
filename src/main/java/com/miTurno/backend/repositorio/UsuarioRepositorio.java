package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.CredencialEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad,Long> {


    UsuarioEntidad findByCredencialEmailAndCredencialPassword(String email, String password);
    List<UsuarioEntidad> findAllByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);
}

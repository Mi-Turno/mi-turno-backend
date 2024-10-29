package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.DiaEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.tipos.DiasEnum;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiaRepositorio extends JpaRepository<DiaEntidad,Long> {
    DiaEntidad findByDia(DiasEnum diasEnum);



}

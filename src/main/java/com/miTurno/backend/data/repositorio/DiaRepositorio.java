package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.DiaEntidad;
import com.miTurno.backend.tipos.DiasEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiaRepositorio extends JpaRepository<DiaEntidad,Long> {
    DiaEntidad findByDia(DiasEnum diasEnum);



}

package com.miTurno.backend.repositorio;


import com.miTurno.backend.entidad.MetodoDePagoEntidad;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodosDePagoRepositorio extends JpaRepository<MetodoDePagoEntidad,Long> {
    MetodoDePagoEntidad findBymetodosDePago(MetodosDePagoEnum MetodosDePagoEnum);
}

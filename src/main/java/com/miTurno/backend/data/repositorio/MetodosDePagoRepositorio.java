package com.miTurno.backend.data.repositorio;


import com.miTurno.backend.data.domain.MetodoDePagoEntidad;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodosDePagoRepositorio extends JpaRepository<MetodoDePagoEntidad,Long> {



    MetodoDePagoEntidad findByMetodoDePago(MetodosDePagoEnum MetodosDePagoEnum);


}

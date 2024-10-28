package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.modelo.DetallesNegocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallesNegocioRepositorio extends JpaRepository<DetallesNegocio,Long> {
    DetallesNegocioEntidad findByNombreNegocio(String nombreNegocio);



    List<ServicioEntidad> findAllServiciosPorNombreNegocio(String nombreNegocio);
}

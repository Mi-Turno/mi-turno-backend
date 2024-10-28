package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.DetallesNegocioEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.modelo.DetallesNegocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallesNegocioRepositorio extends JpaRepository<DetallesNegocioEntidad,Long> {
    DetallesNegocioEntidad findByNombreNegocio(String nombreNegocio);

    // Consulta para obtener los servicios por nombre de negocio
//    @Query("SELECT s FROM ServicioEntidad AS s " +
//            "JOIN Servicios_x_Negocios sxn ON s.idServicio = sxn.idServicio " +
//            "JOIN DetallesNegocios dn ON sxn.idNegocio = dn.idNegocio " +
//            "WHERE dn.nombreNegocio = :nombreNegocio")
//    List<ServicioEntidad> findAllServiciosPorNombreNegocio(String nombreNegocio);


}

package com.miTurno.backend.repositorio.pivotRepositorios;

import com.miTurno.backend.entidad.pivotEntidad.ServiciosXProfesionalEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicioXProfesionalesRepositorio extends JpaRepository<ServiciosXProfesionalEntidad,Long> {

    @Query("SELECT s FROM ServicioEntidad AS s " +
            "JOIN ServiciosXProfesionalEntidad AS sxp ON s.idServicio = sxp.idServicio " +
            "JOIN UsuarioEntidad AS u ON sxp.idProfesional = u.idUsuario " +
            "WHERE  u.idUsuario = :id_profesional")
    List<Long> findAllServiciosByProfesional(@Param("id_profesional")Long id_profesional);

}

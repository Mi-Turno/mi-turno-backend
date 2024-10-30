package com.miTurno.backend.repositorio.pivotRepositorios;

import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.entidad.pivotEntidad.ProfesionalesXNegocioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfesionalesXNegocioRepositorio extends JpaRepository<ProfesionalesXNegocioEntidad,Long> {
    @Query("SELECT up FROM UsuarioEntidad AS up " +
            "JOIN ProfesionalesXNegocioEntidad AS pxn ON up.idUsuario = pxn.idProfesional " +
            "JOIN UsuarioEntidad AS un ON pxn.idNegocio = un.idUsuario " +
            "WHERE un.idUsuario = :idNegocio")
    List<UsuarioEntidad> obtenerTodosLosProfesionalesPorIdNegocio(@Param("idNegocio")Long idNegocio);
}

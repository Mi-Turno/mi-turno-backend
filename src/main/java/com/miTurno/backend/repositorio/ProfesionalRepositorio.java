package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesionalRepositorio extends JpaRepository<ProfesionalEntidad,Long> {
    List<ProfesionalEntidad> findAllByNegocioEntidad_IdUsuario(Long idNegocio);
    ProfesionalEntidad findByIdUsuarioAndNegocioEntidad_IdUsuario( Long idProfesional,Long idNegocio);
    List<ProfesionalEntidad> findAllByNegocioEntidad_IdUsuarioAndCredenciales_Estado(Long idNegocio, Boolean estado);
}

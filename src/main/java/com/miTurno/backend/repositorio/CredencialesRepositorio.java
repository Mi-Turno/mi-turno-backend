package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.CredencialesEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CredencialesRepositorio extends JpaRepository<CredencialesEntidad,Long> {

    Optional<CredencialesEntidad> findByTelefono(String telefono);
    Optional<CredencialesEntidad>findByEmail(String email);
    List<CredencialesEntidad>findAllByRolEntidad_Rol(RolUsuarioEnum rolUsuarioEnum);

    boolean existsByEmailAndTelefono(@Email String email, String telefono);
    boolean existsByRolEntidad_RolAndUsuario_Nombre(RolUsuarioEnum rolEntidad_rol, String usuario_nombre);

    //Optional<CredencialesEntidad>findByIdAndRolEntidad_Rol(Long idNegocio, RolUsuarioEnum rolUsuarioEnum);
    //List <UsuarioEntidad> findByRolEntidad_RolAndEstado (RolUsuarioEnum rol, Boolean estado);
    List <CredencialesEntidad> findAllByRolEntidad_RolAndEstado (RolUsuarioEnum rol, Boolean estado);
    List<CredencialesEntidad>findAllByEstado(Boolean estado);
}

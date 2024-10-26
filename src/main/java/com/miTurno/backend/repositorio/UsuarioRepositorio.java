package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.modelo.Usuario;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad,Long> {

    boolean existsByEmail(@Email String email);
    boolean existsByCelular(String celular);
    boolean existsByCelularAndEmail(@Email String email, String celular);
}

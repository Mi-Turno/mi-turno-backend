package com.miTurno.backend.repositorio;

import com.miTurno.backend.entidad.ClienteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<ClienteEntidad,Long> {
    ClienteEntidad findByCredenciales_EmailAndCredenciales_Password(String email, String password);
}

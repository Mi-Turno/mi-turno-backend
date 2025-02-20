package com.miTurno.backend.data.repositorio;

import com.miTurno.backend.data.domain.ClienteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<ClienteEntidad,Long> {
    ClienteEntidad findByCredencial_EmailAndCredencial_Password(String email, String password);
    ClienteEntidad findFirstByCredencial_EmailContainingOrderByIdDesc(String emailPart);

}

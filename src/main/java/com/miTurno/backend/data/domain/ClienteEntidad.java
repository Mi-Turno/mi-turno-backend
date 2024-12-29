package com.miTurno.backend.data.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name="clientes")
@Setter
@Getter
@SuperBuilder
public class ClienteEntidad extends UsuarioEntidad{

    //listado de turnos
    @OneToMany(mappedBy = "clienteEntidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TurnoEntidad>listadoDeTurnos;

    public ClienteEntidad() {
    super();
    }
}

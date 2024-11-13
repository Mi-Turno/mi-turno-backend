package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Builder;
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
    @OneToMany(mappedBy = "cliente_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TurnoEntidad>listadoDeTurnos;

    public ClienteEntidad() {
    super();
    }
}

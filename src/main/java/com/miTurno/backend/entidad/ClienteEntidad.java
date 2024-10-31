package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="clientes")
@Setter
@Getter
@Builder
public class ClienteEntidad extends UsuarioEntidad{

    //listado de turnos

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TurnoEntidad>listadoDeTurnos;

    public ClienteEntidad() {

    }
}

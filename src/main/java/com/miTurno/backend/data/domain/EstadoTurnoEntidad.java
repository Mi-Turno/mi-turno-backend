package com.miTurno.backend.data.domain;


import com.miTurno.backend.tipos.EstadoTurnoEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "estados")
public class EstadoTurnoEntidad {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private EstadoTurnoEnum estadoTurno;

    @OneToMany(mappedBy = "estadoTurno",fetch = FetchType.LAZY)
    private List<TurnoEntidad> turnoEntidad;// Lista de turnos que tienen este estado

    //constructores
    public EstadoTurnoEntidad() {
    }

    public EstadoTurnoEntidad(EstadoTurnoEnum estadoTurno) {
        this.estadoTurno = estadoTurno;
    }

    public EstadoTurnoEntidad(Long id, EstadoTurnoEnum estadoTurno) {
        this.id = id;
        this.estadoTurno = estadoTurno;
    }

    public EstadoTurnoEntidad(Long id, EstadoTurnoEnum estadoTurno, List<TurnoEntidad> turnoEntidad) {
        this.id = id;
        this.estadoTurno = estadoTurno;
        this.turnoEntidad = turnoEntidad;
    }

    @Override
    public String toString() {
        return "EstadoTurnoEntidad{" +
                "id=" + id +
                ", estadoTurno=" + estadoTurno +
                ", turnoEntidad=" + turnoEntidad +
                '}';
    }
}

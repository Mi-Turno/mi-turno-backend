package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name="horarios")
@Setter
@Getter
@Builder
public class HorarioProfesionalEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private ProfesionalEntidad profesionalEntidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dia_id")
    private DiaEntidad diaEntidad;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;


    @Column
    private Boolean estado;

    public HorarioProfesionalEntidad() {}

    public HorarioProfesionalEntidad(Long id, ProfesionalEntidad profesionalEntidad, DiaEntidad diaEntidad, LocalTime horaInicio, Boolean estado) {
        this.id = id;
        this.profesionalEntidad = profesionalEntidad;
        this.diaEntidad = diaEntidad;
        this.horaInicio = horaInicio;

        this.estado = estado;
    }
}



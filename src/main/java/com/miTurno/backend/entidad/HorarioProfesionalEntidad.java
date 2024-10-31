package com.miTurno.backend.entidad;

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
    private Long idHorario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_profesional", nullable = false)
    private ProfesionalEntidad profesionalEntidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dia")
    private DiaEntidad diaEntidad;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "estado")
    private Boolean estado;

    public HorarioProfesionalEntidad() {}

    public HorarioProfesionalEntidad(Long idHorario, ProfesionalEntidad profesionalEntidad, DiaEntidad diaEntidad, LocalTime horaInicio, LocalTime horaFin, Boolean estado) {
        this.idHorario = idHorario;
        this.profesionalEntidad = profesionalEntidad;
        this.diaEntidad = diaEntidad;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }
}



package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private ProfesionalEntidad profesionalEntidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dia")
    private DiaEntidad diaEntidad;

    @Column(name = "hora_inicio", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;


//    @Column(name = "hora_fin", nullable = false)
//    private LocalTime horaFin;

    @Column(name = "estado")
    private Boolean estado;

    public HorarioProfesionalEntidad() {}

    public HorarioProfesionalEntidad(Long idHorario, ProfesionalEntidad profesionalEntidad, DiaEntidad diaEntidad, LocalTime horaInicio, Boolean estado) {
        this.idHorario = idHorario;
        this.profesionalEntidad = profesionalEntidad;
        this.diaEntidad = diaEntidad;
        this.horaInicio = horaInicio;

        this.estado = estado;
    }
}



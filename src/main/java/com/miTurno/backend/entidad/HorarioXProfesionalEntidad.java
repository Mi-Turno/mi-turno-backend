package com.miTurno.backend.entidad;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@Table(name = "horarios_x_profesional")
public class HorarioXProfesionalEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorarioXProfesional;


    //@Column(name="id_usuario",nullable = false)
    @JoinColumn(name = "id_usuario")
    private Long idProfesional;


    @ManyToOne(fetch = FetchType.LAZY)//Carga luego
    @JoinColumn(name = "id_dia",nullable = true)
    private DiaEntidad diaEntidad;


    @Column(name="horarios")
    @Temporal(TemporalType.TIME)
    private LocalTime horario;

    public HorarioXProfesionalEntidad(){

    }

    public HorarioXProfesionalEntidad(Long idHorarioXProfesional, Long idProfesional, DiaEntidad diaEntidad, LocalTime horario) {
        this.idHorarioXProfesional = idHorarioXProfesional;
        this.idProfesional = idProfesional;
        this.diaEntidad = diaEntidad;
        this.horario = horario;
    }
}

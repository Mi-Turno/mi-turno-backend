package com.miTurno.backend.entidad.pivotEntidad;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miTurno.backend.entidad.DiaEntidad;
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
    //@ManyToOne(fetch = FetchType.LAZY)todo descomentar cuando haya usuarios
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario")
    private Long idProfesional;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dia_id")//si no funcion agregar , referencedColumnName = "id"
    @JsonIgnore //todo sacar cuando haya usuarios
    private DiaEntidad diaEntidad; // Aquí se mantiene la entidad


    @Column(name="horarios")
    @Temporal(TemporalType.TIME)
    private LocalTime horario;

    public HorarioXProfesionalEntidad(Long idHorarioXProfesional, Long idProfesional, DiaEntidad diaEntidad, LocalTime horario) {
        this.idHorarioXProfesional = idHorarioXProfesional;
        this.idProfesional = idProfesional;
        this.diaEntidad = diaEntidad;
        this.horario = horario;
    }
    public HorarioXProfesionalEntidad(){

    }
}

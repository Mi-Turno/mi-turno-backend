package com.miTurno.backend.entidad;

import com.miTurno.backend.tipos.MetodosDePagoEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "turnos")
@Getter
@Setter
@Builder
public class TurnoEntidad {

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    @Column(name = "id_profesional")
    private Long idProfesional;
    @Column(name = "id_cliente")
    private Long idCliente;
    @Column(name = "id_negocio")
    private Long idNegocio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Temporal(TemporalType.TIME)
    @Column(name = "horarios")
    private LocalTime horario;

    @Column(name="estados")
    private Boolean estado;

    @Column(name = "metodos_de_pagos")
    private MetodosDePagoEnum metodosDePagoEnum;
    //constructores
    public TurnoEntidad() {

    }
    public TurnoEntidad(Long idTurno, Long idProfesional, Long idCliente, Long idNegocio, LocalDate fechaInicio, LocalTime horario,Boolean estado,MetodosDePagoEnum metodosDePagoEnum) {
        this.idTurno = idTurno;
        this.idProfesional = idProfesional;
        this.idCliente = idCliente;
        this.idNegocio = idNegocio;
        this.fechaInicio = fechaInicio;
        this.horario = horario;
        this.estado = estado;
        this.metodosDePagoEnum = metodosDePagoEnum;
    }
}

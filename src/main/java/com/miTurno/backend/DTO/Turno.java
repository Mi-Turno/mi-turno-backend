package com.miTurno.backend.DTO;


import com.miTurno.backend.tipos.MetodosDePagoEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Turno {
    //atributos
    private Long idTurno;
    private Long idServicio;
    private Long idCliente;
    private Long idNegocio;

    private MetodosDePagoEnum metodosDePagoEnum;
    private LocalDate fechaInicio;
    private HorarioProfesional horarioProfesional;
    private Long idProfesional;
    private Boolean estado;

    //constructor


    public Turno(){

    }

    public Turno(Long idTurno, Long idServicio, Long idCliente, Long idNegocio, MetodosDePagoEnum metodosDePagoEnum, LocalDate fechaInicio, HorarioProfesional horarioProfesional, Long idProfesional, Boolean estado) {
        this.idTurno = idTurno;
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.idNegocio = idNegocio;
        this.metodosDePagoEnum = metodosDePagoEnum;
        this.fechaInicio = fechaInicio;
        this.horarioProfesional = horarioProfesional;
        this.idProfesional = idProfesional;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "estado=" + estado +
                ", idTurno=" + idTurno +
                ", idServicio=" + idServicio +
                ", idCliente=" + idCliente +
                ", idNegocio=" + idNegocio +
                ", metodosDePagoEnum=" + metodosDePagoEnum +
                ", fechaInicio=" + fechaInicio +
                ", horarioProfesional=" + horarioProfesional +
                ", idProfesional=" + idProfesional +
                '}';
    }
}

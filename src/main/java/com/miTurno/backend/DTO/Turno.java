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
    private Long idHorarioProfesional;
    private Long idProfesional;
    private Boolean estado;

    //constructor


    public Turno(){

    }

    public Turno(Long idTurno, Long idServicio, Long idCliente, Long idNegocio, MetodosDePagoEnum metodosDePagoEnum, LocalDate fechaInicio, Long idHorarioProfesional, Long idProfesional, Boolean estado) {
        this.idTurno = idTurno;
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.idNegocio = idNegocio;
        this.metodosDePagoEnum = metodosDePagoEnum;
        this.fechaInicio = fechaInicio;
        this.idHorarioProfesional = idHorarioProfesional;
        this.idProfesional = idProfesional;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "idTurno=" + idTurno +
                ", idServicio=" + idServicio +
                ", idCliente=" + idCliente +
                ", idNegocio=" + idNegocio +
                ", metodosDePagoEnum=" + metodosDePagoEnum +
                ", fechaInicio=" + fechaInicio +
                ", idHorarioProfesional=" + idHorarioProfesional +
                ", idProfesional=" + idProfesional +
                ", estado=" + estado +
                '}';
    }
}

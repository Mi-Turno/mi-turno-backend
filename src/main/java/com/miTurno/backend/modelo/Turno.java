package com.miTurno.backend.modelo;


import com.miTurno.backend.tipos.MetodosDePagoEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class Turno {
    //atributos
    private Long idTurno;
    private Long idProfesional;
    private Long idCliente;
    private Long idNegocio;
    private LocalDate fechaInicio;
    private LocalTime horario;
    private Boolean estado;
    private MetodosDePagoEnum metodosDePagoEnum;
    //constructor
    public Turno(Long idTurno,Long idProfesional, Long idCliente, Long idNegocio, LocalDate fechaInicio, LocalTime horario,Boolean estado,MetodosDePagoEnum metodosDePagoEnum) {
        this.idTurno=idTurno;
        this.idProfesional = idProfesional;
        this.idCliente = idCliente;
        this.idNegocio = idNegocio;
        this.fechaInicio = fechaInicio;
        this.horario = horario;
        this.estado = estado;
        this.metodosDePagoEnum = metodosDePagoEnum;
    }
    public Turno(){

    }
    @Override
    public String toString() {
        return "Turno{" +
                "idTurno=" + idTurno +
                ", idProfesional=" + idProfesional +
                ", idCliente=" + idCliente +
                ", idNegocio=" + idNegocio +
                ", fechaInicio=" + fechaInicio +
                ", horario=" + horario +
                ", estado=" + estado +
                '}';
    }
}

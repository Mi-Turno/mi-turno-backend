package com.miTurno.backend.DTO;


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
    private Long idServicio;
    private Long idCliente;
    private Long idNegocio;
    private MetodosDePagoEnum metodosDePagoEnum;
    private LocalDate fechaInicio;
    private HorarioProfesional horarioProfesional;

    private Boolean estado;

    //constructor

    public Turno(){

    }

}

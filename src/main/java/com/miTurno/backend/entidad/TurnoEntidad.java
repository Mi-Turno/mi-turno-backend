package com.miTurno.backend.entidad;

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
    @Column(name = "horario")
    private LocalTime horario;

    @Column(name="estado")
    private Boolean estado;

    //constructores
    public TurnoEntidad() {

    }
    public TurnoEntidad(Long idTurno, Long idProfesional, Long idCliente, Long idNegocio, LocalDate fechaInicio, LocalTime horario,Boolean estado) {
        this.idTurno = idTurno;
        this.idProfesional = idProfesional;
        this.idCliente = idCliente;
        this.idNegocio = idNegocio;
        this.fechaInicio = fechaInicio;
        this.horario = horario;
        this.estado = estado;
    }
}

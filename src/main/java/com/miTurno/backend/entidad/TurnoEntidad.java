package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "turnos")
@Getter
@Setter
@Builder
public class TurnoEntidad {

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Relación con Servicio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ServicioEntidad idServicio;

    // Relación con MetodoDePago
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MetodoDePagoEntidad metodoDePagoEntidad;

    //relacion con cliente muchos a uno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ClienteEntidad clienteEntidad;


    //relacion con negocio muchos a uno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private NegocioEntidad negocioEntidad;


    //relacion con profesional muchos a uno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ProfesionalEntidad profesionalEntidad;

    //hora de inicio del turno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private HorarioProfesionalEntidad horarioProfesionalEntidad;

    //fecha de inicio del turno
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;


    //estado del turno
    @Column(name="estado")
    private Boolean estado;


    //constructores
    public TurnoEntidad() {

    }

    public TurnoEntidad(Long id, ServicioEntidad idServicio, MetodoDePagoEntidad metodoDePagoEntidad, ClienteEntidad clienteEntidad, NegocioEntidad negocioEntidad, ProfesionalEntidad profesionalEntidad, HorarioProfesionalEntidad horarioProfesionalEntidad, LocalDate fechaInicio, Boolean estado) {
        this.id = id;
        this.idServicio = idServicio;
        this.metodoDePagoEntidad = metodoDePagoEntidad;
        this.clienteEntidad = clienteEntidad;
        this.negocioEntidad = negocioEntidad;
        this.profesionalEntidad = profesionalEntidad;
        this.horarioProfesionalEntidad = horarioProfesionalEntidad;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
    }
}

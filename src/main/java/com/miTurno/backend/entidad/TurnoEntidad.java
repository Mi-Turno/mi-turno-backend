package com.miTurno.backend.entidad;

import com.miTurno.backend.DTO.HorarioProfesional;
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
    @Column(name = "id_turno")
    private Long idTurno;


    // Relación con Servicio
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_servicio", nullable = false)
    private ServicioEntidad idServicio;

    // Relación con MetodoDePago
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_metodo_de_pago", nullable = false)
    private MetodoDePagoEntidad metodoDePagoEntidad;


    //relacion con cliente muchos a uno
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntidad clienteEntidad;


    //relacion con negocio muchos a uno
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_negocio", nullable = false)
    private NegocioEntidad negocioEntidad;


    //relacion con profesional muchos a uno
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_profesional",nullable = false)
    private ProfesionalEntidad profesionalEntidad;

    //fecha de inicio del turno
    @ManyToOne(fetch = FetchType.EAGER)
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private HorarioProfesionalEntidad horarioProfesionalEntidad;

    //estado del turno
    @Column(name="estados")
    private Boolean estado;


    //constructores
    public TurnoEntidad() {

    }

}

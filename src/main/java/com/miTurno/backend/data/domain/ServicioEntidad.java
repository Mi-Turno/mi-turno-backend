package com.miTurno.backend.data.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "servicios")
@AllArgsConstructor
public class ServicioEntidad {

    @Id
    @Column(name = "servicio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 3, max = 50)
    @Column(name = "nombre_servicio")
    private String nombre;


    @Column(name = "duracion")
    private Integer duracion;


    @Column(name = "precio")
    private Double precio;

    @Column(name = "foto_servicio")
    private String fotoServicio;

    @Column(name = "estado")
    private Boolean estado;

    // Relación con los profesionales que ofrecen este servicio
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ProfesionalEntidad> profesionales;

    //relacion con el negocio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negocio_id",nullable = false) // Clave foránea hacia Negocio
    private NegocioEntidad negocioEntidad;

    @OneToMany(mappedBy = "idServicio" ,fetch = FetchType.LAZY)
    private List<TurnoEntidad> turnos;

    public ServicioEntidad() {

    }

    //constructores


}

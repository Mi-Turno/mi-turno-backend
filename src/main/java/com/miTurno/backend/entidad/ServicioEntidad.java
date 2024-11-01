package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "servicios")
public class ServicioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;


    @Size(min = 3, max = 50)
    @Column(name = "nombre_servicio")
    private String nombre;


    @Column(name = "duracion")
    private Integer duracion;


    @Column(name = "precio")
    private Double precio;


    @Column(name = "estado")
    private Boolean estado;

    // Relación con los profesionales que ofrecen este servicio
    @ManyToMany(mappedBy = "listaServiciosEntidad", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProfesionalEntidad> profesionales;

    //relacion con el negocio
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_negocio", nullable = false) // Clave foránea hacia Negocio
    private NegocioEntidad negocioEntidad;


    //constructores
    public ServicioEntidad() {
    }

    public ServicioEntidad(Long idServicio, String nombre, Integer duracion, Double precio, Boolean estado, List<ProfesionalEntidad> profesionales, NegocioEntidad negocioEntidad) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
        this.estado = estado;
        this.profesionales = profesionales;
        this.negocioEntidad = negocioEntidad;
    }
}

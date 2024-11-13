package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long id;


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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfesionalEntidad> profesionales;

    //relacion con el negocio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false) // Clave foránea hacia Negocio
    @JsonBackReference
    private NegocioEntidad negocioEntidad;


    //constructores
    public ServicioEntidad() {
    }

    public ServicioEntidad(Long id, String nombre, Integer duracion, Double precio, Boolean estado, List<ProfesionalEntidad> profesionales, NegocioEntidad negocioEntidad) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
        this.estado = estado;
        this.profesionales = profesionales;
        this.negocioEntidad = negocioEntidad;
    }
}

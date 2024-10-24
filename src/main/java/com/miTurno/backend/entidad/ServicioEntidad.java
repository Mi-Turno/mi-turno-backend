package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Entity
@Table(name = "servicios")
public class ServicioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Setter
    @Size(min = 3, max = 50)
    @Column(name = "nombre_servicio")
    private String nombre;

    @Setter
    @Column(name = "duracion")
    private Integer duracion;

    @Setter
    @Column(name = "precio")
    private Double precio;

    @Setter
    @Column(name = "estado")
    private Boolean estado;

    //constructores
    public ServicioEntidad() {
    }

    public ServicioEntidad(Long idServicio, String nombre, Integer duracion, Double precio, Boolean estado) {
        this.duracion = duracion;
        this.estado = estado;
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
    }
}

package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "servicios")
@Getter
@Setter
public class ServicioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Size(min = 3, max = 50)
    @Column(name = "nombre_servicio")
    private String nombre;

    @Size(min= 3, max= 50)
    @Column(name = "duracion")
    private String duracion;

    @Column(name = "precio")
    private Double precio;

    public ServicioEntidad() {
    }

    public ServicioEntidad(String duracion, String nombre, Double precio) {
        this.duracion = duracion;
        this.nombre = nombre;
        this.precio = precio;
    }
}

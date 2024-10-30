package com.miTurno.backend.entidad.pivotEntidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@Table(name = "Servicios_x_profesional")
public class ServiciosXProfesionalEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Servicios_x_profesional;

    @Column(name = "id_profesional")
    private Long profesional;

    @Column(name = "id_servicio")
    private Long servicio;

    @Column(name = "precio")
    private Double precio;
    public ServiciosXProfesionalEntidad() {
    }

    public ServiciosXProfesionalEntidad(Long servicios_x_profesional, Long profesional, Long servicio, Double precio) {
        Servicios_x_profesional = servicios_x_profesional;
        this.profesional = profesional;
        this.servicio = servicio;
        this.precio = precio;
    }
}

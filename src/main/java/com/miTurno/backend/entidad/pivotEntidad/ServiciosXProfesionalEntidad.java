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
    private Long idProfesional;

    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "precio")
    private Double precio;
    public ServiciosXProfesionalEntidad() {
    }

    public ServiciosXProfesionalEntidad(Long servicios_x_profesional, Long idProfesional, Long idServicio, Double precio) {
        Servicios_x_profesional = servicios_x_profesional;
        this.idProfesional = idProfesional;
        this.idServicio = idServicio;
        this.precio = precio;
    }
}

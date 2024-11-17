package com.miTurno.backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Servicio {
    //atributos
    private Long idServicio;
    private Long idNegocio;
    private String nombre;
    private Integer duracion;
    private Double precio;
    private Boolean estado;
    
    //constructores
    
    public Servicio() {
    }

    public Servicio(Long idServicio, Long idNegocio, String nombre, Integer duracion, Double precio, Boolean estado) {
        this.idServicio = idServicio;
        this.idNegocio = idNegocio;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
        this.estado = estado;
    }

    //metodos


    @Override
    public String toString() {
        return "Servicio{" +
                "idServicio=" + idServicio +
                ", idNegocio=" + idNegocio +
                ", nombre='" + nombre + '\'' +
                ", duracion=" + duracion +
                ", precio=" + precio +
                ", estado=" + estado +
                '}';
    }
}

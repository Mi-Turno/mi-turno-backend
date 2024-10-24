package com.miTurno.backend.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Servicio {
    //atributos
    private Long idServicio;
    private String nombre;
    private Integer duracion;
    private Double precio;
    private Boolean estado;
    
    //constructores
    
    public Servicio() {
    }

    public Servicio(Long idServicio, String nombre, Integer duracion, Double precio, Boolean estado) {
        this.duracion = duracion;
        this.estado = estado;
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    //metodos


    @Override
    public String toString() {
        return "Servicio{" +
                "duracion=" + duracion +
                ", idServicio=" + idServicio +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", estado=" + estado +
                '}';
    }
}

package com.miTurno.backend.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Servicio {
    //atributos
    private Long idServicio;
    private String nombre;
    private String duracion;
    private Double precio;
    
    //constructores
    
    public Servicio() {
    }

    public Servicio(String duracion, Long idServicio, String nombre, Double precio) {
        this.duracion = duracion;
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    //metodos


    @Override
    public String toString() {
        return "Servicio{" +
                "duracion='" + duracion + '\'' +
                ", idServicio=" + idServicio +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}

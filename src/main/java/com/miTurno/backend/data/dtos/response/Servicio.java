package com.miTurno.backend.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Servicio {
    //atributos
    private Long idServicio;
    private Long idNegocio;
    private String nombre;
    private Integer duracion;
    private Double precio;
    private Boolean estado;
    private String fotoServicio;
    //constructores
    
    public Servicio() {
    }

    //metodos
    @Override
    public String toString() {
        return "Servicio{" +
                "duracion=" + duracion +
                ", idServicio=" + idServicio +
                ", idNegocio=" + idNegocio +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", estado=" + estado +
                ", fotoServicio='" + fotoServicio + '\'' +
                '}';
    }
}

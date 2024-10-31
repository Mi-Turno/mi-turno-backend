package com.miTurno.backend.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Negocio extends Usuario{
    //atributos

    private String rubro;
    private String calle;
    private String altura;
    private String detalle;


    //constructor


    public Negocio() {
    }
}

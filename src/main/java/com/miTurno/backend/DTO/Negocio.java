package com.miTurno.backend.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
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

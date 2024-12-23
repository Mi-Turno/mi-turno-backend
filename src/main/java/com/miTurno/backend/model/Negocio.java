package com.miTurno.backend.model;


import com.miTurno.backend.entidad.ClienteEntidad;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class Negocio extends Usuario{
    //atributos

    private String rubro;
    private String calle;
    private String altura;
    private String detalle;

    private List<Profesional> profesionales;
    private List<Servicio>servicios;
    private List<ClienteEntidad> clientes;

    //constructor


    public Negocio() {
    }
}

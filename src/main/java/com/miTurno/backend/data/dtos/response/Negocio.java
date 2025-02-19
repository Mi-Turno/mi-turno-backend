package com.miTurno.backend.data.dtos.response;


import com.miTurno.backend.data.domain.ClienteEntidad;
import com.miTurno.backend.data.domain.MetodoDePagoEntidad;
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
    private List<MetodoDePagoEntidad> metodosDePago;

    //constructor


    public Negocio() {
    }

    @Override
    public String toString() {
        return "Negocio{" +
                "rubro='" + rubro + '\'' +
                ", calle='" + calle + '\'' +
                ", altura='" + altura + '\'' +
                ", detalle='" + detalle + '\'' +
                ", profesionales=" + profesionales +
                ", servicios=" + servicios +
                ", clientes=" + clientes +
                ", metodosDePago=" + metodosDePago +
                '}';
    }
}

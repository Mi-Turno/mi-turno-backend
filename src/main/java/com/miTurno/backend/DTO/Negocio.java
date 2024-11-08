package com.miTurno.backend.DTO;


import com.miTurno.backend.entidad.ClienteEntidad;
import com.miTurno.backend.entidad.ProfesionalEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.mapper.ProfesionalMapper;
import lombok.Builder;
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
    private List<ServicioEntidad>servicios;
    private List<ClienteEntidad> clientes;

    //constructor


    public Negocio() {
    }
}

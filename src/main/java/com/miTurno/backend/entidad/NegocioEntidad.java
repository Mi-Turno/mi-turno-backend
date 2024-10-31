package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@Table(name = "negocios")
public class NegocioEntidad extends UsuarioEntidad{


    @Column(name = "rubro")
    private String rubro;

    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private String altura;

    @Column(name = "detalle")
    private String detalle;



    //profesionales del negocio
    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfesionalEntidad> profesionales;

    //servicios que ofrece el negocio
    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServicioEntidad> servicios;

    //listado de clientes
    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsuarioEntidad> clientes;



    public NegocioEntidad() {
    }



}

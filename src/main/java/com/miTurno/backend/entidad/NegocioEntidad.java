package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_negocios")
    private List<ProfesionalEntidad> profesionales;

    //servicios que ofrece el negocio
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicios")
    private List<ServicioEntidad> servicios;

    //listado de clientes
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_negocio")
    private List<UsuarioEntidad> clientes;



    public NegocioEntidad() {
        super();
    }



}

package com.miTurno.backend.data.domain;

import jakarta.persistence.*;
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



    //todo, ESTOS ARREGLOS SE PUEDEN NO RETORNAR CON @JSONIGNORE

    //profesionales del negocio
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id")
    private List<ProfesionalEntidad> profesionales;

    //servicios que ofrece el negocio
    @OneToMany(fetch = FetchType.LAZY)
    //@JoinColumn(name = "servicio_id")
    private List<ServicioEntidad> servicios;

    //listado de clientes
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private List<ClienteEntidad> clientes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "negocio_metodos_pago",
            joinColumns = @JoinColumn(name = "negocio_id"),
            inverseJoinColumns = @JoinColumn(name = "metodo_pago_id")
    )
    private List<MetodoDePagoEntidad> metodosDePago;

    public NegocioEntidad() {
        super();
    }



}

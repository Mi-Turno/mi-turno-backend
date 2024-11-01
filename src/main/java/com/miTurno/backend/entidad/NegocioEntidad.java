package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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



    //todo, ESTOS ARREGLOS SE PUEDEN NO RETORNAR CON @JSONIGNORE

    //profesionales del negocio
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_negocio")
    @JsonManagedReference
    private List<ProfesionalEntidad> profesionales;

    //servicios que ofrece el negocio
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio")
    @JsonManagedReference
    private List<ServicioEntidad> servicios;

    //listado de clientes
    @OneToMany(fetch = FetchType.LAZY)
    //@JoinColumn(name = "id_usuario")
    @JsonManagedReference
    private List<UsuarioEntidad> clientes;



    public NegocioEntidad() {
        super();
    }



}

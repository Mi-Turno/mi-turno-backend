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
@Table(name = "detalles_negocios")
public class DetallesNegocioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallesNegocio;

    @Column(name = "rubro")
    private String rubro;

    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private String altura;

    @Column(name = "detalle")
    private String detalle;

//    @ManyToOne(targetEntity = UsuarioEntidad.class)
//    private Long idNegocio;


    public DetallesNegocioEntidad() {
    }

    public DetallesNegocioEntidad(Long idDetallesNegocio, String rubro, String calle, String altura, String detalle) {
        this.idDetallesNegocio = idDetallesNegocio;
        this.rubro = rubro;
        this.calle = calle;
        this.altura = altura;
        this.detalle = detalle;
    }

}

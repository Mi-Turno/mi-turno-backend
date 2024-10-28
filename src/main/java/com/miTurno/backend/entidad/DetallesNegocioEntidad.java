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

    @Column(name ="nombre_negocio", unique = true)
    private String nombreNegocio;

    @Column(name = "id_negocio")
    private Long idNegocio;

//    @OneToMany()
//    private List<UsuarioEntidad> profesionales;

    public DetallesNegocioEntidad() {
    }

    public DetallesNegocioEntidad(Long idDetallesNegocio, String rubro, String nombreNegocio, Long idNegocio) {
        this.idDetallesNegocio = idDetallesNegocio;
        this.rubro = rubro;
        this.nombreNegocio = nombreNegocio;
        this.idNegocio = idNegocio;
    }
}

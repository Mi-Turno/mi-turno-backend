package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
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


    public DetallesNegocioEntidad() {
    }

    public DetallesNegocioEntidad(Long idDetallesNegocio, String rubro, String nombreNegocio, Long idNegocio) {
        this.idDetallesNegocio = idDetallesNegocio;
        this.rubro = rubro;
        this.nombreNegocio = nombreNegocio;
        this.idNegocio = idNegocio;
    }
}

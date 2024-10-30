package com.miTurno.backend.entidad.pivotEntidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@Table(name = "profesionales_x_negocio")
public class ProfesionalesXNegocioEntidad {

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profesionales_x_negocio;

    @Column(name = "id_profesional")
    private Long idProfesional;

    @Column(name = "id_negocio")
    private Long idNegocio;


    public ProfesionalesXNegocioEntidad(Long profesionales_x_negocio, Long idProfesional, Long idNegocio) {
        this.profesionales_x_negocio = profesionales_x_negocio;
        this.idProfesional = idProfesional;
        this.idNegocio = idNegocio;
    }
}

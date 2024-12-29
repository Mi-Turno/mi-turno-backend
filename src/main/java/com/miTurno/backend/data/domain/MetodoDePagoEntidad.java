package com.miTurno.backend.data.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "metodos_de_pagos")
public class MetodoDePagoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonValue
    public String getNombre() {
        return metodoDePago.name();
    }


    @Column(name = "metodo_de_pago",unique = true,nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodosDePagoEnum metodoDePago;

    @OneToMany(mappedBy = "metodoDePagoEntidad")
    private List<TurnoEntidad> listaTurnos;

    public MetodoDePagoEntidad(Long id, MetodosDePagoEnum metodoDePago, List<TurnoEntidad> listaTurnos) {
        this.id = id;
        this.metodoDePago = metodoDePago;
        this.listaTurnos = listaTurnos;
    }

    public MetodoDePagoEntidad() {

    }

    public MetodoDePagoEntidad(MetodosDePagoEnum metodoDePago) {
        this.metodoDePago = metodoDePago;
    }
}

package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonValue;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "metodos_de_pagos")
public class MetodoDePagoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metodo_de_pago",unique = true,nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodosDePagoEnum metodosDePago;

    @JsonValue
    public String getNombre() {
        return metodosDePago.name();
    }

    public MetodoDePagoEntidad(MetodosDePagoEnum metodosDePago) {
        this.metodosDePago = metodosDePago;
    }

    public MetodoDePagoEntidad(Long id, MetodosDePagoEnum metodosDePago) {
        this.id = id;
        this.metodosDePago = metodosDePago;
    }

    public MetodoDePagoEntidad() {
    }
}

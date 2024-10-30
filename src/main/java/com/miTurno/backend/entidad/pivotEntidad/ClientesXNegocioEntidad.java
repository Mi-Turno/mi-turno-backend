package com.miTurno.backend.entidad.pivotEntidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@Table(name = "clientes_x_negocio")
public class ClientesXNegocioEntidad {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientes_x_negocio;

    @Column(name = "id_cliente")
    private Long cliente;

    @Column(name = "id_negocio")
    private Long negocio;

    public ClientesXNegocioEntidad() {
    }

    public ClientesXNegocioEntidad(Long clientes_x_negocio, Long cliente, Long negocio) {
        this.clientes_x_negocio = clientes_x_negocio;
        this.cliente = cliente;
        this.negocio = negocio;
    }
}

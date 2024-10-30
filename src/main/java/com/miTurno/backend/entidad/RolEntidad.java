package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonValue;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "roles")
public class RolEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", unique = true, nullable = false)
    private RolUsuarioEnum rol;


    @JsonValue
    public String getNombre() {
        return rol.name();
    }

    //constructores

    public RolEntidad(Long id_rol, RolUsuarioEnum rol) {
        this.id_rol = id_rol;
        this.rol = rol;
    }

    public RolEntidad(RolUsuarioEnum rol) {
        this.rol = rol;
    }
    public RolEntidad() {

    }
}

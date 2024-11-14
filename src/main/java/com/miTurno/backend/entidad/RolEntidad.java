package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonValue;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "roles")
public class RolEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RolUsuarioEnum rol;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "rolEntidad")
    private List<UsuarioEntidad> usuarios; // Lista de usuarios que tienen este rol

    public RolEntidad(RolUsuarioEnum rolUsuarioEnum) {
        rol=rolUsuarioEnum;
    }


    //constructores


    public RolEntidad() {
        super();
    }

    public RolEntidad(Long id, RolUsuarioEnum rol) {
        this.id = id;
        this.rol = rol;
    }

    public RolEntidad(Long id, RolUsuarioEnum rol, List<UsuarioEntidad> usuarios) {
        this.id = id;
        this.rol = rol;
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "RolEntidad{" +
                "id=" + id +
                ", rol=" + rol +
                '}';
    }
}

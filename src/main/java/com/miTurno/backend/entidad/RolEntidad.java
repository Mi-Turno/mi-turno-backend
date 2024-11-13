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

    //@OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private List<UsuarioEntidad> usuarios; // Lista de usuarios que tienen este rol

    public RolEntidad(RolUsuarioEnum rolUsuarioEnum) {
        rol=rolUsuarioEnum;
    }

    @JsonValue
    public String getNombre() {
        return rol.name();
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
}

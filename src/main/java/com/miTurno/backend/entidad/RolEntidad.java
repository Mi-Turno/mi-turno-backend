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
    @Column(name = "id_rol")
    private Long id_rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", unique = true, nullable = false)
    private RolUsuarioEnum rol;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsuarioEntidad> usuarios; // Lista de usuarios que tienen este rol

    @JsonValue
    public String getNombre() {
        return rol.name();
    }

    //constructores

    public RolEntidad() {}
    public RolEntidad(RolUsuarioEnum rol){}
}

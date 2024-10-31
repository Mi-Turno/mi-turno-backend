package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="crendenciales")
@Setter
@Getter
@Builder
public class CredencialesEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credenciales")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

//    @OneToOne
//    @JoinColumn(name = "id_usuario")
//    @JsonIgnore // Para evitar el ciclo en serialización/deserialización
//    private UsuarioEntidad usuario;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER para cargar el rol junto con el usuario
    @JoinColumn(name = "id_rol", nullable = false) // Define la clave foránea a RolEntidad
    private RolEntidad rolEntidad;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    public CredencialesEntidad() {

    }

}

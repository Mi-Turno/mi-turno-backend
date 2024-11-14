package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String telefono;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id",nullable = false) // Define la clave foránea a UsuarioEntidad
    private UsuarioEntidad usuario;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER para cargar el rol junto con el usuario
    @JoinColumn(name = "rol_id",nullable = false) // Define la clave foránea a RolEntidad
    private RolEntidad rolEntidad;

    @Column(nullable = false)
    private Boolean estado;

    public CredencialesEntidad() {

    }

    public CredencialesEntidad(Long id, String email, String password, String telefono, UsuarioEntidad usuario, RolEntidad rolEntidad, Boolean estado) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.usuario = usuario;
        this.rolEntidad = rolEntidad;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CredencialesEntidad{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rolEntidad=" + rolEntidad +
                ", estado=" + estado +
                '}';
    }
}



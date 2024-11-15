package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="credenciales")
@Setter
@Getter
@Builder
public class CredencialEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String telefono;


    @OneToOne(mappedBy = "credencial")
    private UsuarioEntidad usuario;

    @Column(nullable = false)
    private Boolean estado;



    public CredencialEntidad() {

    }

    public CredencialEntidad(Long id, String email, String password, String telefono, UsuarioEntidad usuario, Boolean estado) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.usuario = usuario;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CredencialEntidad{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", usuario=" + usuario +
                ", estado=" + estado +
                '}';
    }
}



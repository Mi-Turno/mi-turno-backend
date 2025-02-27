package com.miTurno.backend.data.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="credenciales")
@Setter
@Getter
@Builder
@AllArgsConstructor
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

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "vencimiento_codigo")
    private LocalDateTime vencimientoCodigo;

    @Column(name="usuario_verificado")
    private Boolean usuarioVerificado;

    public CredencialEntidad() {

    }



}



package com.miTurno.backend.model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Credencial {

    private Long idCredencial;
    private String email;
    private String password;
    private String telefono;
    private Boolean estado;

    public Credencial() {
    }

    public Credencial(Long idCredencial, String email, String password, String telefono, Boolean estado) {
        this.idCredencial = idCredencial;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.estado = estado;
    }
}

package com.miTurno.backend.DTO;
import com.miTurno.backend.tipos.RolUsuarioEnum;
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
    private RolUsuarioEnum rolUsuario;
    private Boolean estado;

    public Credencial() {
    }
}

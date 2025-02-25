package com.miTurno.backend.data.dtos.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Credencial {

    private Long idCredencial;
    private String email;
    private String password;
    private String telefono;
    private Boolean estado;
    private String codigo;
    private LocalDateTime vencimientoCodigo;
    private Boolean usuarioVerificado;

    public Credencial() {
    }


}

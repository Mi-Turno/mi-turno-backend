package com.miTurno.backend.data.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificarUsuarioRequest {
    private String email;
    private String codigoVerificacion;

    public VerificarUsuarioRequest() {
    }

}

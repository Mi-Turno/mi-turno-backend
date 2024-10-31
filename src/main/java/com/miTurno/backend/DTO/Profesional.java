package com.miTurno.backend.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Profesional extends Usuario{

    private Long idNegocio;

    public Profesional() {
    }
}

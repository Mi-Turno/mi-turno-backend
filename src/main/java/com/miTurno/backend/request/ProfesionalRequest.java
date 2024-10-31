package com.miTurno.backend.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Schema(description = "Requisitos para la creacion de un profesional")
@SuperBuilder
@Getter
@Setter
public class ProfesionalRequest extends UsuarioRequest{

    //datos profesional

    @Schema(description = "ID del negocio al que pertenece el profesional", example = "1")
    @NotNull
    private Long idNegocio;

    public ProfesionalRequest() {
    super();
    }

    public ProfesionalRequest(Long idNegocio) {
        super();
        this.idNegocio = idNegocio;
    }
}

package com.miTurno.backend.request;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
public class TurnoRequest {


    @Schema(description = "ID del servicio relacionado", example = "2")
    private Long idServicio; // ID del servicio relacionado

    @Schema(description = "ID del método de pago relacionado", example = "3")
    private Long idMetodoDePago; // ID del método de pago relacionado

    @Schema(description = "ID del cliente relacionado", example = "4")
    private Long idCliente; // ID del cliente relacionado

    @Schema(description = "ID del negocio relacionado", example = "5")
    private Long idNegocio; // ID del negocio relacionado

    @Schema(description = "Horario del profesional con los datos requeridos")
    private HorarioProfesionalRequest horarioProfesional;


    // Constructor vacío
    public TurnoRequest() {}

    public TurnoRequest(Long idServicio, Long idMetodoDePago, Long idCliente, Long idNegocio, HorarioProfesionalRequest horarioProfesional) {
        this.idServicio = idServicio;
        this.idMetodoDePago = idMetodoDePago;
        this.idCliente = idCliente;
        this.idNegocio = idNegocio;
        this.horarioProfesional = horarioProfesional;
    }
}

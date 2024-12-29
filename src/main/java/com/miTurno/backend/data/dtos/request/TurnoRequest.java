package com.miTurno.backend.data.dtos.request;
import com.miTurno.backend.data.dtos.response.HorarioProfesional;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TurnoRequest {


    @Schema(description = "ID del servicio relacionado", example = "1")
    private Long idServicio; // ID del servicio relacionado

    @Schema(description = "Enum metodo de pago relacionado", example = "TARJETA_CREDITO")
    private MetodosDePagoEnum metodosDePagoEnum; // ID del método de pago relacionado

    @Schema(description = "ID del cliente relacionado", example = "3")
    private Long idCliente; // ID del cliente relacionado


//    @Schema(description = "ID del negocio relacionado", example = "1")
//    private Long idNegocio; // ID del negocio relacionado

    @Schema(description = "ID profesional con los datos requeridos",example = "2")
    private Long idProfesional;

    @Schema(description = "Horario del profesional con los datos requeridos")
    private HorarioProfesional horarioProfesional;


    @Schema(description = "Fecha de inicio que tendra el turno")
    private LocalDate fechaInicio;

    @Schema(description = "Estado del turno",example = "RESERVADO")
    private EstadoTurnoEnum estadoTurno;

    // Constructor vacío
    public TurnoRequest() {}

    public TurnoRequest(Long idServicio, MetodosDePagoEnum metodosDePagoEnum, Long idCliente, Long idProfesional, HorarioProfesional horarioProfesional, LocalDate fechaInicio, EstadoTurnoEnum estadoTurno) {
        this.idServicio = idServicio;
        this.metodosDePagoEnum = metodosDePagoEnum;
        this.idCliente = idCliente;
        this.idProfesional = idProfesional;
        this.horarioProfesional = horarioProfesional;
        this.fechaInicio = fechaInicio;
        this.estadoTurno = estadoTurno;
    }
}

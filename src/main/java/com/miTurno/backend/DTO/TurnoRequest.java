package com.miTurno.backend.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class TurnoRequest {

    @Schema(description = "ID del profesional al que le corresponde el turno",example = "1")
    private Long idProfesional;

    @Schema(description = "ID del cliente al que pertenece",example = "2")
    private Long idCliente;

    @Schema(description = "ID del negocio al que corresponen los turnos", example = "5")
    private Long idNegocio;

    @Schema(description = "Dia del turno" ,example = "2024-10-26")
    private LocalDate fechaInicio;/**LOCALTIME ES MEJOR PARA MANEJAR HORAS*/

    @Schema(description = "Horario del turno" ,example = "13:00")
    private LocalTime horario;/**LOCALTIME ES MEJOR PARA MANEJAR HORAS*/

}

package com.miTurno.backend.DTO;
import com.miTurno.backend.entidad.HorarioProfesionalEntidad;
import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.entidad.TurnoEntidad;
import com.miTurno.backend.request.HorarioProfesionalRequest;
import com.miTurno.backend.request.ServicioRequest;
import com.miTurno.backend.request.TurnoRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class Profesional extends Usuario{

    private Long idNegocio;

    private List<ServicioEntidad> listaServicios;
    private List<HorarioProfesionalEntidad> horariosDisponibles;
    private List<TurnoEntidad> turnosAgendados;

    public Profesional() {
    }
}

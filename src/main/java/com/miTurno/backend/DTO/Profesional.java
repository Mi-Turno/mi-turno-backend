package com.miTurno.backend.DTO;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class Profesional extends Usuario{

    private Long idNegocio;

    private List<Servicio> listaServicios;
    private List<HorarioProfesional> horariosDisponibles;
    private List<Turno> turnosAgendados;

    public Profesional() {
    }
}

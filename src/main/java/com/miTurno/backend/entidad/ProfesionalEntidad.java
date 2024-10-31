package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@Table(name = "profesionales")
public class ProfesionalEntidad extends UsuarioEntidad{



    // negocio al que esta relacionado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_negocio", nullable = false) // Clave foránea hacia Negocio
    private NegocioEntidad idNegocio;

    // Servicios específicos que ofrece el profesional
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "profesional_servicio",
            joinColumns = @JoinColumn(name = "id_profesional"),
            inverseJoinColumns = @JoinColumn(name = "id_servicio")
    )
    private List<ServicioEntidad> servicios;

    // listado de horarios DISPONIBLES
    @OneToMany(mappedBy = "profesional", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<HorarioProfesionalEntidad> horariosDisponibles;

    //lista de turnos AGENDADOS
    @OneToMany(mappedBy = "profesional", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TurnoEntidad> turnosAgendados;


    public ProfesionalEntidad() {
    }
}

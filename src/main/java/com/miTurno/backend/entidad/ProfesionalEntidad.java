package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "profesionales")
public class ProfesionalEntidad extends UsuarioEntidad{



    // negocio al que esta relacionado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false) // Clave foránea hacia Negocio
    private NegocioEntidad negocioEntidad;

    // Servicios específicos que ofrece el profesional
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "profesional_servicio",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<ServicioEntidad> listaServiciosEntidad;

    // listado de horarios DISPONIBLES
   // @OneToMany(mappedBy = "profesional", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "horario_id")
    private List<HorarioProfesionalEntidad> horariosDisponibles;

    //lista de turnos AGENDADOS
    //@OneToMany(mappedBy = "profesional", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "profesional_id",fetch = FetchType.LAZY)
    private List<TurnoEntidad> turnosAgendados;


    public ProfesionalEntidad() {
        super();
    }
}

package com.miTurno.backend.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class Cliente extends Usuario{

    private List<Turno> turnos;
    public Cliente() {
    }
}

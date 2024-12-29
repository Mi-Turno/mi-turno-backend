package com.miTurno.backend.data.dtos.response;


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

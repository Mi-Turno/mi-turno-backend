package com.miTurno.backend.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Cliente extends Usuario{

    public Cliente() {
    }
}

package com.miTurno.backend.excepciones;

import com.miTurno.backend.tipos.RolUsuarioEnum;
import lombok.Getter;

@Getter
public class RolIncorrectoException extends RuntimeException{
    private final RolUsuarioEnum rolUsuarioEnum;
    private final RolUsuarioEnum rolRequerido;


    public RolIncorrectoException(RolUsuarioEnum rolRequerido,RolUsuarioEnum rolUsuarioEnum) {
        super("Solo se aceptan este tipo de rol: "+rolRequerido.name()+" y se recibio: "+rolUsuarioEnum.name());
        this.rolUsuarioEnum = rolUsuarioEnum;
        this.rolRequerido = rolRequerido;
    }


}

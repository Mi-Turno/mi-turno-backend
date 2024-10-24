package com.miTurno.backend.excepcion;

public class ServicioNoExisteException extends RuntimeException{
    private final Long id;

    public ServicioNoExisteException(Long id) {
        super("Servicio con ID "+id+" no encontrado.");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

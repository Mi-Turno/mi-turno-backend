package com.miTurno.backend.excepciones;

public class TurnoNoExistenteException extends RuntimeException{
    private final Long id;

    public TurnoNoExistenteException(Long id) {
        super("Turno con ID "+id+" no encontrado.");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

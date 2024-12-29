package com.miTurno.backend.excepciones;

public class UsuarioNoExistenteException extends RuntimeException{
    private final Long id;

    public UsuarioNoExistenteException(Long id) {
        super("Usuario con ID "+id+" no encontrado.");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

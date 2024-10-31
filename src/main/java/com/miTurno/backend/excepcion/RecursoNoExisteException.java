package com.miTurno.backend.excepcion;

public class RecursoNoExisteException extends RuntimeException{

    private final String dato;

    public RecursoNoExisteException(String dato) {
        super("El dato: "+dato+" no existe.");
        this.dato = dato;
    }

    public String getDato() {
        return dato;
    }
}

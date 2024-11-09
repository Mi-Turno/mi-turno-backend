package com.miTurno.backend.excepcion;

public class TelefonoYaExisteException extends RuntimeException{
    private final String nroCelular;
    public TelefonoYaExisteException(String nroCelular) {
        super("El usuario con el celular:"+nroCelular+" ya existe.");
        this.nroCelular = nroCelular;
    }

    public String getNroCelular() {
        return nroCelular;
    }
}

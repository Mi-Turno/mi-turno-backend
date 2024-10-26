package com.miTurno.backend.excepcion;

public class CelularYaExisteException extends RuntimeException{
    private final String nroCelular;
    public CelularYaExisteException(String nroCelular) {
        super("El usuario con el celular:"+nroCelular+" ya existe.");
        this.nroCelular = nroCelular;
    }

    public String getNroCelular() {
        return nroCelular;
    }
}

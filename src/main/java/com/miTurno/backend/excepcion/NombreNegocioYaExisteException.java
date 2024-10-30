package com.miTurno.backend.excepcion;

public class NombreNegocioYaExisteException extends RuntimeException{

    private final String nombreNegocio;

    public NombreNegocioYaExisteException(String nombreNegocio) {
        super("El negocio con el nombre: "+ nombreNegocio+" ya existe.");
        this.nombreNegocio = nombreNegocio;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }
}

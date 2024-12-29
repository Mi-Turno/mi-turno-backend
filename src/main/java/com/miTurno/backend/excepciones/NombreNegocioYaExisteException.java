package com.miTurno.backend.excepciones;

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

package com.miTurno.backend.excepcion;

public class ContraseniaIncorrectaException extends RuntimeException{

    String contrasenia;

    public ContraseniaIncorrectaException(String contrasenia) {
        super("Contraseña " + contrasenia + "inexistente o invalido");
        this.contrasenia = contrasenia;
    }
    public String getContrasenia() {
        return contrasenia;
    }
}

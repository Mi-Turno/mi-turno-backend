package com.miTurno.backend.excepciones;

public class UsuarioNoVerificadoException extends RuntimeException{

    public UsuarioNoVerificadoException() {
        super("Usuario no verificado. Por favor revise su cuenta.");
    }

    public UsuarioNoVerificadoException(String mensaje) {
        super(mensaje);
    }
}

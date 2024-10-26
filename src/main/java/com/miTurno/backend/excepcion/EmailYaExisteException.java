package com.miTurno.backend.excepcion;

public class EmailYaExisteException extends RuntimeException{
    private final String email;

    public EmailYaExisteException(String email) {
        super("El usuario con el email:"+email+" ya existe.");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}

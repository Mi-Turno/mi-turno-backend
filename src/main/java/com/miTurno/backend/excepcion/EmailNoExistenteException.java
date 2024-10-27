package com.miTurno.backend.excepcion;

public class EmailNoExistenteException extends RuntimeException{
    String email;

    public EmailNoExistenteException(String email) {
        super("Email " + email + "inexistente o invalido");
    this.email = email;
    }
    public String getEmail() {
        return email;
    }
}

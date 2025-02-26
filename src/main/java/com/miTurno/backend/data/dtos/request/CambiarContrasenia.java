package com.miTurno.backend.data.dtos.request;

public class CambiarContrasenia {

    private String token;
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContrasenia() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

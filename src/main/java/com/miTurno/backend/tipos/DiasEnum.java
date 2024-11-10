package com.miTurno.backend.tipos;

public enum DiasEnum {
    DOMINGO,
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO;

    public static DiasEnum fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IllegalArgumentException("El número no corresponde a ningún día de la semana");
        }
        return values()[ordinal];
    }
}

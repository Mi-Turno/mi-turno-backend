package com.miTurno.backend.excepcion;

import lombok.Getter;

@Getter
public class NombreNoExisteException extends RuntimeException{

        private final String nombre;

        public NombreNoExisteException(String nombre) {
            super("El nombre: "+nombre+" no existe.");
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

}

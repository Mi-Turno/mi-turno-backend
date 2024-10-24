package com.miTurno.backend.manejador;


import com.miTurno.backend.excepcion.ServicioNoExisteException;
import com.miTurno.backend.excepcion.UsuarioNoExistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice  //Esta notación permite definir un controlador para todas las excepciones de spring
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(UsuarioNoExistenteException.class)
    public ResponseEntity<Map<String, Long>> manejarUsuarioNoEncontrado(UsuarioNoExistenteException ex) {
        Map<String, Long> errores = new HashMap<>();
        errores.put("id", ex.getId());
        return  new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServicioNoExisteException.class)
    public ResponseEntity<Map<String,Long>> manejarServicioNoEncontrado(ServicioNoExisteException ex){
        Map<String, Long> errores = new HashMap<>();
        errores.put("id", ex.getId());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }
}

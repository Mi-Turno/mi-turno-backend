package com.miTurno.backend.excepciones.manejador;


import com.miTurno.backend.excepciones.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice  //Esta notación permite definir un controlador para todas las excepciones de spring
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of( "error", "Entidad no encontrada",
                        "mensaje", ex.getMessage(),
                        "timestamp", LocalDateTime.now() ));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "Entidad ya existente",
                        "mensaje", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Argumento no válido",
                        "mensaje", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Error interno del servidor",
                        "mensaje", "Ocurrió un error inesperado",
                        "detalle", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }


    @ExceptionHandler(RolIncorrectoException.class)
    public ResponseEntity<?> RolIncorrectoException(RolIncorrectoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Error de roles",
                        "detalle", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

}

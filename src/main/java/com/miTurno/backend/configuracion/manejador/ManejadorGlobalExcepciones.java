package com.miTurno.backend.configuracion.manejador;


import com.miTurno.backend.excepciones.*;
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

    @ExceptionHandler(ServicioNoExisteException.class)
    public ResponseEntity<Map<String,Long>> manejarServicioNoEncontrado(ServicioNoExisteException ex){
        Map<String, Long> errores = new HashMap<>();
        errores.put("id", ex.getId());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailYaExisteException.class)
    public ResponseEntity<Map<String,String>> manejarEmailYaExiste(EmailYaExisteException ex){
        Map<String, String> errores = new HashMap<>();
        errores.put("email", ex.getEmail());
        return new ResponseEntity<>(errores, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TelefonoYaExisteException.class)
    public ResponseEntity<Map<String,String>> CelularYaExisteException(TelefonoYaExisteException ex){
        Map<String, String> errores = new HashMap<>();
        errores.put("telefono", ex.getNroCelular());
        return new ResponseEntity<>(errores, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NombreNegocioYaExisteException.class)
    public ResponseEntity<Map<String,String>> NombreNegocioYaExisteException(NombreNegocioYaExisteException ex){
        Map<String, String> errores = new HashMap<>();
        errores.put("nombreNegocio", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RolIncorrectoException.class)
    public ResponseEntity<Map<String,String>> RolIncorrectoException(RolIncorrectoException ex){
        Map<String, String> errores = new HashMap<>();
        errores.put("Error de roles", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NombreNoExisteException.class)
    public ResponseEntity<Map<String,String>> NombreNoExisteException(NombreNoExisteException ex){
        Map<String, String> errores = new HashMap<>();
        errores.put("Nombre", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecursoNoExisteException.class)
    public ResponseEntity<Map<String,String>> RecursoNoExisteException(RecursoNoExisteException ex){
        Map<String, String> errores = new HashMap<>();
        errores.put("recurso ", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContraseniaIncorrectaException.class)
    public ResponseEntity<Map<String,String>> ContraseniaIncorrectaException(ContraseniaIncorrectaException ex){
        Map<String, String> errores = new HashMap<>();
        errores.put("Contraseña ", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

}

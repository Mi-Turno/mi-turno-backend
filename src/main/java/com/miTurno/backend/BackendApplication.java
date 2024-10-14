package com.miTurno.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		//http://localhost:8080/swagger-ui/index.html |entrar a swagger
		//http://localhost:8080/h2-console | entrar en H2 bbdd

		//todo poner que el swagger no pida el idUsuario
		//probar en la bbdd
		/*INSERT INTO USUARIOS (NOMBRE , APELLIDO , CORREO_ELECTRONICO , CELULAR ,FECHA_NACIMIENTO , ROL ) VALUES
		('Juan', 'Pérez', 'juan.perez@example.com', '123456789', '1990-05-15', 'CLIENTE'),
		('María', 'Gómez', 'maria.gomez@example.com', '987654321', '1992-08-20', 'ADMIN'),
		('Pedro', 'Sánchez', 'pedro.sanchez@example.com', '555123456', '1988-12-01', 'CLIENTE');*/

	}

}

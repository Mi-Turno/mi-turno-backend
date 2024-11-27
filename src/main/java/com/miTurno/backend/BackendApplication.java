package com.miTurno.backend;

//import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendApplication {

	public static void main(String[] args) {

		//http://localhost:8080/swagger-ui/index.html |entrar a swagger
		//http://localhost:8080/h2-console | entrar en H2 bbdd
		//nombre de usuario por defecto es "user" y la contraseña es generada aleatoriamente en cada inicio de la aplicación.



		//todo cambiar esto porque es una mala practica, pero funciona por el momento
		// Pasar las variables del .env al entorno
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
		System.setProperty("MAIL_PORT", dotenv.get("MAIL_PORT"));
		System.setProperty("MAIL_PROTOCOL", dotenv.get("MAIL_PROTOCOL"));
		System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
		System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
		System.setProperty("MAIL_SMTP_AUTH", dotenv.get("MAIL_SMTP_AUTH"));
		System.setProperty("MAIL_STARTTLS_ENABLE", dotenv.get("MAIL_STARTTLS_ENABLE"));
		System.setProperty("MAIL_STARTTLS_REQUIRED", dotenv.get("MAIL_STARTTLS_REQUIRED"));
		System.setProperty("MAIL_SSL_TRUST", dotenv.get("MAIL_SSL_TRUST"));
		SpringApplication.run(BackendApplication.class, args);
	}

}

package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EnviarCorreoService {
    @Autowired
    private JavaMailSender enviadorMail;
    @Async
    public void EnviarCorreoService(EmailRequest emailRequest) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(emailRequest.getEmail());//correo destino
        email.setFrom(emailRequest.getEmailNegocio()); // correo origen
        email.setSubject("Confirmación de turno para " + emailRequest.getServicio());

        String cuerpoCorreo = String.format(
                "%s\nServicio: %s\nPrecio: %.2f\nDirección: %s",
                emailRequest.getMensaje(),
                emailRequest.getServicio(),
                emailRequest.getPrecio(),
                emailRequest.getDireccion()
        );
        email.setText(cuerpoCorreo);//agrego el cuerpo

        // Enviar el correo
        enviadorMail.send(email);
    }
}

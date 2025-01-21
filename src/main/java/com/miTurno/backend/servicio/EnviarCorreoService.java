package com.miTurno.backend.servicio;

import com.miTurno.backend.data.dtos.request.EmailContactoRequest;
import com.miTurno.backend.data.dtos.request.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EnviarCorreoService {

    private final JavaMailSender enviadorMail;

    @Autowired
    public EnviarCorreoService(JavaMailSender enviadorMail) {
        this.enviadorMail = enviadorMail;
    }

    @Async
    public void enviarCorreo(EmailRequest emailRequest)
    {
        SimpleMailMessage email = new SimpleMailMessage();

        // Origen y destino
        email.setTo(emailRequest.getEmail()); // (cliente)
        email.setFrom(emailRequest.getEmailNegocio()); //(negocio)

        //asunto
        email.setSubject("Confirmación de turno para " + emailRequest.getServicio());

        // Formato para enviar el email
        String cuerpoCorreo;
        cuerpoCorreo = String.format(
                "Hola, %s.\n\n" +
                        "Su turno ha sido reservado correctamente.\n\n" +
                        "Detalles del turno:\n" +
                        "-----------------------\n" +
                        "Servicio: %s\n" +
                        "Profesional: %s\n" +
                        "Precio: $%.2f\n" +
                        "Fecha: %s\n" +
                        "Horario: %s\n" +
                        "Dirección: %s\n\n" +
                        "Ubicación: %s\n\n" +
                        "Mensaje adicional: %s\n\n" +
                        "Gracias por elegirnos.\n" +
                        "Saludos,\n" +
                        "%s",
                emailRequest.getEmail(),
                emailRequest.getServicio(),
                emailRequest.getNombreProfesional(),
                emailRequest.getPrecio(),
                emailRequest.getFecha(),
                emailRequest.getHorario(),
                emailRequest.getDireccion(),
                emailRequest.getUbicacion(),
                emailRequest.getMensaje(),
                emailRequest.getEmailNegocio()
        );
        //agrego el cuerpo que cree arriba
        email.setText(cuerpoCorreo);

        //envio el correo
        enviadorMail.send(email);
    }

    @Async
    public void EnviarCorreoDeContacto(EmailContactoRequest emailContactoRequest){
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo("miturno.flf@gmail.com"); //Mi-turno
        email.setFrom(emailContactoRequest.getEmail());//Cliente

        email.setSubject(String.format("%s de %s tiene un mensaje para  Mi Turno", emailContactoRequest.getNombre(), emailContactoRequest.getNegocio()));
        email.setText(String.format("Email: %s \n\n Mensaje: %s", emailContactoRequest.getEmail(), emailContactoRequest.getMensaje()));

        enviadorMail.send(email);

    }

    @Async
    public void enviarCorreoDeVerificacion(String correoCliente, String titulo,String texto) throws MessagingException {
        MimeMessage message = enviadorMail.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(correoCliente);
        helper.setSubject(titulo);
        helper.setText(texto,true);

        enviadorMail.send(message);
    }



}

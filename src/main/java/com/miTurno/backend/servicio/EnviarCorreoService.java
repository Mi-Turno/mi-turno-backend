package com.miTurno.backend.servicio;

import com.miTurno.backend.data.domain.TurnoEntidad;
import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.dtos.request.EmailCancelacionRequest;
import com.miTurno.backend.data.dtos.request.EmailContactoRequest;
import com.miTurno.backend.data.dtos.request.EmailRequest;
import com.miTurno.backend.data.repositorio.TurnoRepositorio;
import com.miTurno.backend.excepciones.CodigoVerificacionException;
import com.miTurno.backend.excepciones.UsuarioNoVerificadoException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnviarCorreoService {

    private final JavaMailSender enviadorMail;
    private final TurnoRepositorio turnoRepositorio;

    @Autowired
    public EnviarCorreoService(JavaMailSender enviadorMail, TurnoRepositorio turnoRepositorio) {
        this.enviadorMail = enviadorMail;
        this.turnoRepositorio = turnoRepositorio;
    }

    @Async
    @Scheduled(fixedRate = 60000) // Revisa cada 1 minutos
    @Transactional // Asegura que la sesión esté abierta durante la operación
    public void enviarCorreosProgramados() {

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime enDosHoras = ahora.plusHours(2);

        // Traemos todos los turnos y los revisamos en memoria
        List<TurnoEntidad> todosLosTurnos = turnoRepositorio.findAll();
        List<TurnoEntidad> turnosProximos = todosLosTurnos.stream()
                .filter(turno -> {
                    // Construimos el LocalDateTime combinando la fecha y la hora del turno
                    LocalDateTime turnoFechaHora = turno.getFechaInicio()
                            .atTime(turno.getHorarioProfesionalEntidad().getHoraInicio());  // Usamos la hora del turno
                    return turnoFechaHora.isAfter(ahora) && turnoFechaHora.isBefore(enDosHoras);
                })
                .toList();

        for (TurnoEntidad turno : turnosProximos) {
            if (!turno.isCorreoEnviado()) { //yo solo quiero enviar mails que no se hayan enviado antes
                try {
                    if(!turno.getClienteEntidad().getCredencial().getEmail().toLowerCase().contains("invitado")){
                        enviarCorreoProgramado(turno);
                        turno.setCorreoEnviado(true);
                        turnoRepositorio.save(turno);
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void enviarCorreoProgramado(TurnoEntidad turno) throws MessagingException {
        MimeMessage mensaje = enviadorMail.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        // Verifica si el correo de cliente está presente
        String clienteEmail = turno.getClienteEntidad().getCredencial().getEmail();
        if (clienteEmail == null || clienteEmail.isEmpty()) {
            return;
        }

        // Configura el destinatario y remitente
        helper.setTo(clienteEmail);  // cliente
        helper.setFrom("miturno.flf@gmail.com"); // tu correo verifica que sea correcto

        // Asunto del correo
        helper.setSubject("📅 Recordatorio de turno en " + turno.getNegocioEntidad().getNombre());

        // Formateamos la fecha y la hora
        String fechaFormateada = turno.getFechaInicio().toString();
        String horaFormateada = turno.getHorarioProfesionalEntidad().getHoraInicio().toString();

        // Construcción del cuerpo HTML
        String cuerpoHtml = "<html>" +
                "<body style='font-family: Arial, sans-serif; text-align: center;'>" +
                "<h2 style='color: #4CAF50;'>¡Recordatorio de tu turno. Te esperamos " + turno.getClienteEntidad().getNombre() + " !" + "</h2>" +
                "<p><strong> Lugar:</strong> " + turno.getNegocioEntidad().getNombre() + "</p>" +
                "<p><strong> Direccion:</strong> " + turno.getNegocioEntidad().getCalle() + " " + turno.getNegocioEntidad().getAltura() + "</p>" +
                "<p><strong> Fecha:</strong> " + fechaFormateada + "</p>" +
                "<p><strong> Hora:</strong> " + horaFormateada + "</p>" +
                "<p><strong> Profesional:</strong> " + turno.getProfesionalEntidad().getNombre() + "</p>" +
                "<p><strong> Precio:</strong> $" + turno.getIdServicio().getPrecio() + "</p>" +
                "<p>¡Nos vemos pronto!</p>" +
                "<p style='color: gray; font-size: 12px;'>Si necesitas cancelar o reprogramar, comunícate con nosotros. Podes cumunicarte aqui " + turno.getNegocioEntidad().getCredencial().getTelefono() + "</p>" +
                "</body></html>";

        // Establecer el contenido del correo
        helper.setText(cuerpoHtml, true);

        // Enviar el correo
        enviadorMail.send(mensaje);
    }

    @Async
    public void enviarCorreoConfirmacion(EmailRequest emailRequest) {
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
    public void EnviarCorreoDeContacto(EmailContactoRequest emailContactoRequest) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo("miturno.flf@gmail.com"); //Mi-turno
        email.setFrom(emailContactoRequest.getEmail());//Cliente

        email.setSubject(String.format("%s de %s tiene un mensaje para  Mi Turno", emailContactoRequest.getNombre(), emailContactoRequest.getNegocio()));
        email.setText(String.format("Email: %s \n\n Mensaje: %s", emailContactoRequest.getEmail(), emailContactoRequest.getMensaje()));

        enviadorMail.send(email);

    }

    @Async
    public void enviarCorreoDeVerificacion(String correoCliente, String titulo, String texto) throws MessagingException {
        MimeMessage message = enviadorMail.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(correoCliente);
        helper.setSubject(titulo);
        helper.setText(texto, true);

        enviadorMail.send(message);
    }

    @Async
    public void enviarCorreoCancelacionNegocio(EmailCancelacionRequest emailRequest)  {
        MimeMessage mensaje = enviadorMail.createMimeMessage();
        MimeMessageHelper helper = null;
        if(emailRequest.getEmailCliente().toLowerCase().contains("invitado"))
        {
            try {
                helper = new MimeMessageHelper(mensaje, true);
                helper.setTo(emailRequest.getEmailCliente());  // email del cliente
                helper.setFrom("miturno.flf@gmail.com");
                helper.setSubject("❌ TURNO CANCELADO");

                String htmlContenido = "<!DOCTYPE html>" +
                        "<html lang='es'>" +
                        "<head>" +
                        "    <meta charset='UTF-8'>" +
                        "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "    <title>Cancelación de Turno</title>" +
                        "</head>" +
                        "<body style='font-family: Arial, sans-serif; background-color: #f8f8f8; padding: 20px;'>" +
                        "    <div style='max-width: 600px; background: white; padding: 20px; border-radius: 8px; " +
                        "box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);'>" +
                        "        <h2 style='color: red; text-align: center;'>  TURNO CANCELADO</h2>" +
                        "        <p>Estimado/a <strong>" + emailRequest.getNombreCliente() + "</strong>,</p>" +
                        "        <p>Lamentamos informarle que su turno ha sido cancelado.</p>" +
                        "        <p>Si necesita más información o desea reprogramar, no dude en comunicarse con nosotros al siguiente número:</p>" +
                        "        <p style='text-align: center; font-size: 18px;'><strong>" + emailRequest.getNumeroSoporte() + "</strong></p>" +
                        "        <p>Gracias por su comprensión.</p>" +
                        "        <p>Atentamente,</p>" +
                        "        <p><strong>El equipo de atención al cliente de "+emailRequest.getNombreNegocio()+"</strong></p>" +
                        "    </div>" +
                        "</body>" +
                        "</html>";

                helper.setText(htmlContenido, true);
                System.out.println("Enviando email negocio");
                enviadorMail.send(mensaje);
            } catch (MessagingException e) {
                System.out.println(e.getMessage());
            }
        }


    }
    @Async
    public void enviarCorreoCancelacionCliente(EmailCancelacionRequest emailRequest)  {
        MimeMessage mensaje = enviadorMail.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mensaje, true);
            helper.setTo(emailRequest.getEmailCliente());  // email del cliente
            helper.setFrom("miturno.flf@gmail.com");
            helper.setSubject("❌ TURNO CANCELADO");

            String htmlContenido = "<!DOCTYPE html>" +
                    "<html lang='es'>" +
                    "<head>" +
                    "    <meta charset='UTF-8'>" +
                    "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                    "    <title>Cancelación de Turno</title>" +
                    "</head>" +
                    "<body style='font-family: Arial, sans-serif; background-color: #f8f8f8; padding: 20px;'>" +
                    "    <div style='max-width: 600px; background: white; padding: 20px; border-radius: 8px; " +
                    "box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);'>" +
                    "        <h2 style='color: red; text-align: center;'>  TURNO CANCELADO</h2>" +
                    "        <p>Estimado/a <strong>" +emailRequest.getNombreCliente() + "</strong>,</p>" +
                    "        <p>Su turno ha sido cancelado correctamente.</p>" +
                    "        <p>Si necesita más información o desea reprogramar, no dude en comunicarse con nosotros al siguiente número:</p>" +
                    "        <p style='text-align: center; font-size: 18px;'><strong> " + emailRequest.getNumeroSoporte() + "</strong></p>" +
                    "        <p>Gracias por su comprensión.</p>" +
                    "        <p>Atentamente,</p>" +
                    "        <p><strong>Puede comunicarse con el equipo de atención al cliente de "+emailRequest.getNombreNegocio()+"</strong></p>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContenido, true);
            System.out.println("Enviando email cliente");
            enviadorMail.send(mensaje);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void enviarMailDeVerificacion(UsuarioEntidad usuario) throws MessagingException {

        //TODO: actualizar con el logo de mi turno
        String subject = "📬 Verificación de Cuenta en Mi Turno";
        String codigoDeVerificacion = "Tu codigo de verificación: " + usuario.getCredencial().getCodigo();

        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Bienvenido a <b style=\"font-size: 2rem\">Mi turno</b> "+ usuario.getNombre()+"!</h2>"
                + "<p style=\"font-size: 16px;\">Por favor ingresa el codigo de verificacion que recibiste</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + codigoDeVerificacion + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        enviarCorreoDeVerificacion(usuario.getCredencial().getEmail(), subject, htmlMessage);
    }


    public void enviarMailDeReestablecerContrasenia(UsuarioEntidad usuarioEntidad){

        if (!usuarioEntidad.getCredencial().getUsuarioVerificado()){
            throw new UsuarioNoVerificadoException();
        }

        String asunto = "🔑 Restablecimiento de Contraseña";
        String urlRestablecimiento = String.format("http://localhost:4200/login/nueva-password?token=%s",usuarioEntidad.getCredencial().getCodigo());
        String cuerpoHtml = "<!DOCTYPE html>" +
                "<html lang='es'>" +
                "<head><meta charset='UTF-8'></head>" +
                "<body style='font-family: Arial, sans-serif; background-color: #e6e6fa; text-align: center; padding: 20px;'>" +
                "<div style='max-width: 600px; margin: auto; background: linear-gradient(to right, #6a5acd, #483d8b); border-radius: 10px; padding: 20px; color: white;'>" +
                "<h1>Restablecimiento de Contraseña</h1>" +
                "<p>Hola, " + usuarioEntidad.getNombre() + ", hemos recibido una solicitud para restablecer tu contraseña.</p>" +
                "<div style='background: white; padding: 20px; border-radius: 10px; color: black;'>" +
                "<p>Para continuar con el proceso, haz clic en el siguiente botón:</p>" +
                "<a href='" + urlRestablecimiento + "' style='display: inline-block; padding: 12px 20px; margin-top: 15px; font-size: 16px; color: white; background-color: #6a5acd; text-decoration: none; border-radius: 5px; font-weight: bold;'>Restablecer Contraseña</a>" +
                "<p>Si no solicitaste este cambio, ignora este mensaje.</p>" +
                "</div>" +
                "<p style='margin-top: 20px; font-size: 12px; opacity: 0.8;'>Este enlace expirará en 24 horas. Si necesitas ayuda, contáctanos.</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage mensaje = enviadorMail.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

            helper.setTo(usuarioEntidad.getCredencial().getEmail());
            helper.setFrom("miturno.flf@gmail.com");
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true);

            enviadorMail.send(helper.getMimeMessage());
        }catch (MessagingException e) {
            System.out.println(e.getMessage());
        }



    }


}




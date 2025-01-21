package com.miTurno.backend.servicio;

import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.dtos.request.VerificarUsuarioRequest;
import com.miTurno.backend.data.repositorio.UsuarioRepositorio;
import com.miTurno.backend.data.dtos.request.UsuarioLoginRequest;
import com.miTurno.backend.excepciones.CodigoVerificacionException;
import com.miTurno.backend.excepciones.UsuarioNoVerificadoException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthService {
    private final UsuarioRepositorio usuarioRepositorio;

    private final AuthenticationManager authenticationManager;
    private final EnviarCorreoService enviarCorreoService;

    @Autowired
    public AuthService(UsuarioRepositorio usuarioRepositorio, AuthenticationManager authenticationManager, EnviarCorreoService enviarCorreoService) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.authenticationManager = authenticationManager;
        this.enviarCorreoService = enviarCorreoService;
    }

    public UsuarioEntidad authenticate(UsuarioLoginRequest input) {
        try {
            // Busca al usuario en la base de datos por su email
            UsuarioEntidad usuarioEntidad= usuarioRepositorio.findByCredencialEmail(input.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Email no fue encontrado en el sistema"));


            //validamos si el email del usuario esta verificado.
//            if (!usuarioEntidad.getCredencial().getEstado()){
//                throw new UsuarioNoVerificadoException();
//            }


            // Autentica las credenciales utilizando el AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
            return usuarioEntidad;

        } catch (BadCredentialsException ex) {
            // Si las credenciales son incorrectas, lanza una excepción personalizada
            throw new BadCredentialsException("Correo o contraseña incorrectos", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error en el proceso de autenticación", ex);
        }
    }

    public Map<String, String> verificarUsuario(VerificarUsuarioRequest input) {
        Map<String, String> response = new HashMap<>();


        UsuarioEntidad usuarioEntidad= usuarioRepositorio.findByCredencialEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email no fue encontrado en el sistema."));

        if (input.getCodigoVerificacion() == null){
            throw new CodigoVerificacionException("Usuario ya verificado");
        }

        //si el codigo de verificacion esta vencido, la logica seria si HOY esta Antes del vtoCdigoVerif
        if (usuarioEntidad.getCredencial().getVencimientoCodigoVerificacion().isBefore(LocalDateTime.now())) {
            throw new CodigoVerificacionException("Codigo de verificacion ha expirado.");
        }

        //si el codigo de verificacion no es igual al que tiene en la bd
        if (!usuarioEntidad.getCredencial().getCodigoVerificacion().equals(input.getCodigoVerificacion())) {
            throw new CodigoVerificacionException("Codigo de verificacion invalido.");
        }


        usuarioEntidad.getCredencial().setEstado(true);
        usuarioEntidad.getCredencial().setCodigoVerificacion(null);
        usuarioEntidad.getCredencial().setVencimientoCodigoVerificacion(null);
        usuarioRepositorio.save(usuarioEntidad);
        response.put("mensaje", "Usuario verificado con éxito.");
        return response;
    }

    public String reenviarCodigoDeVerificacion(String email) throws MessagingException {
        UsuarioEntidad usuarioEntidad= usuarioRepositorio.findByCredencialEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email no fue encontrado en el sistema."));

        if (usuarioEntidad.isEnabled()) {
            throw new CodigoVerificacionException("Cuenta ya esta verificada");
        }

        usuarioEntidad.getCredencial().setCodigoVerificacion(generarCodigoDeVerificacion());

        //le damos mas tiempo, 20 mins
        usuarioEntidad.getCredencial().setVencimientoCodigoVerificacion(LocalDateTime.now().plusMinutes(20));

        enviarMailDeVerificacion(usuarioEntidad);

        usuarioRepositorio.save(usuarioEntidad);

        return "Codigo de verificación reenviado con éxito.";
    }

    public void enviarMailDeVerificacion(UsuarioEntidad usuario) throws MessagingException {

        //TODO: actualizar con el logo de mi turno
        String subject = "Verificación de Cuenta en Mi Turno";
        String codigoDeVerificacion = "Tu codigo de verificación: " + usuario.getCredencial().getCodigoVerificacion();

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

        enviarCorreoService.enviarCorreoDeVerificacion(usuario.getCredencial().getEmail(), subject, htmlMessage);
    }


    public String generarCodigoDeVerificacion() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }



}

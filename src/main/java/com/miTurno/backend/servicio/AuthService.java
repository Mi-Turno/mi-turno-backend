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
import java.util.UUID;

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

    public HashMap<String, String> generarTokenOlvidasteContrasenia(String email) throws MessagingException {
        boolean flag =false;

        UsuarioEntidad usuarioEntidad= usuarioRepositorio.findByCredencialEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email no fue encontrado en el sistema"));

        if (!usuarioEntidad.getCredencial().getUsuarioVerificado()){
            reenviarCodigoDeVerificacion(email);
            throw new UsuarioNoVerificadoException("El correo del usuario no esta verificado, es necesaria la verificacion para reestablecer la contraseña.");
        }

        //generamos los codigos necesarios
        usuarioEntidad.getCredencial().setCodigo(UUID.randomUUID().toString());
        usuarioEntidad.getCredencial().setVencimientoCodigo(LocalDateTime.now().plusMinutes(20));
        usuarioRepositorio.save(usuarioEntidad);

        //enviamos el mail


        //si salio bien
        flag = true;
        HashMap<String, String> response = new HashMap<>();
        response.put("exito", String.valueOf(flag));

        return response;

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

        //si el usuario ya se encuentra verificado
        if (usuarioEntidad.getCredencial().getUsuarioVerificado()){
            throw new CodigoVerificacionException("Usuario ya esta verificado");
        }

        //si el codigo de verificacion esta vencido, la logica seria si HOY esta Antes del vtoCdigoVerif
        if (usuarioEntidad.getCredencial().getVencimientoCodigo().isBefore(LocalDateTime.now())) {
            throw new CodigoVerificacionException("Codigo de verificacion ha expirado.");
        }

        //si el codigo de verificacion no es igual al que tiene en la bd
        if (!usuarioEntidad.getCredencial().getCodigo().equals(input.getCodigo())) {
            throw new CodigoVerificacionException("Codigo de verificacion invalido.");
        }


        usuarioEntidad.getCredencial().setUsuarioVerificado(true);
        usuarioEntidad.getCredencial().setCodigo(null);
        usuarioEntidad.getCredencial().setVencimientoCodigo(null);
        usuarioRepositorio.save(usuarioEntidad);
        response.put("mensaje", "Usuario verificado con éxito.");
        return response;
    }

    public String reenviarCodigoDeVerificacion(String email) throws MessagingException {
        UsuarioEntidad usuarioEntidad= usuarioRepositorio.findByCredencialEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email no fue encontrado en el sistema."));

       if (usuarioEntidad.getCredencial().getUsuarioVerificado()) {
            throw new CodigoVerificacionException("Usuario ya esta verificado");
        }

        usuarioEntidad.getCredencial().setCodigo(generarCodigoDeVerificacion());

        //le damos mas tiempo, 20 mins
        usuarioEntidad.getCredencial().setVencimientoCodigo(LocalDateTime.now().plusMinutes(20));

        enviarCorreoService.enviarMailDeVerificacion(usuarioEntidad);

        usuarioRepositorio.save(usuarioEntidad);

        return "Codigo de verificación reenviado con éxito.";
    }



    public String generarCodigoDeVerificacion() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }



}

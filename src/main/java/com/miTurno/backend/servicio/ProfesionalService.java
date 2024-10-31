package com.miTurno.backend.servicio;

import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.excepcion.RolIncorrectoException;
import com.miTurno.backend.request.ProfesionalRequest;
import com.miTurno.backend.request.UsuarioRequest;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.stereotype.Service;

@Service
public class ProfesionalService {


    //POST profesional

    public Usuario crearUnprofesional(ProfesionalRequest profesionalRequest) throws RolIncorrectoException {

        RolUsuarioEnum rolUsuarioEnum = profesionalRequest.getRolUsuarioEnum();

        if (rolUsuarioEnum != RolUsuarioEnum.PROFESIONAL) {
            throw new RolIncorrectoException(RolUsuarioEnum.PROFESIONAL, rolUsuarioEnum);
        }

        // Crear RolEntidad usando el RolUsuarioEnum
        RolEntidad rolEntidad = new RolEntidad();
        rolEntidad.setRol(rolUsuarioEnum);

        // Crear UsuarioRequest usando RolEntidad
        UsuarioRequest usuarioRequest = UsuarioRequest.builder()
                .rolUsuarioEnum(rolEntidad.getRol())
                .nombre(profesionalRequest.getNombre())
                .apellido(profesionalRequest.getApellido())
                .email(profesionalRequest.getEmail())
                .password(profesionalRequest.getPassword())
                .telefono(profesionalRequest.getTelefono())
                .fechaNacimiento(profesionalRequest.getFechaNacimiento())
                .build();

        // Crear el usuario
        Usuario profesional = crearUnUsuario(usuarioRequest);


        //el id relacion lo agarro de onda
        Long idRelacion =profesionalesXNegocioService.crearProfesionalPorNegocio(profesional.getIdUsuario(), profesionalRequest.getIdNegocio());


        // Crear los detalles del negocio
        return profesional;
    }
}

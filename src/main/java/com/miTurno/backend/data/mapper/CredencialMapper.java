package com.miTurno.backend.data.mapper;

import com.miTurno.backend.data.dtos.model.Credencial;
import com.miTurno.backend.data.domain.CredencialEntidad;
import com.miTurno.backend.data.dtos.request.CredencialRequest;
import org.springframework.stereotype.Component;

@Component
public class CredencialMapper {


    public CredencialMapper(){}

    //request a Credencial

    public Credencial toModel(CredencialRequest credencialRequest){

        return Credencial.builder()
                .email(credencialRequest.getEmail())
                .password(credencialRequest.getPassword())
                .telefono(credencialRequest.getTelefono())
//                .rolUsuario(credencialRequest.getRolUsuarioEnum())
                .estado(true)
                .build();

    }

    //entidad a Credencial

    public Credencial toModel(CredencialEntidad credencialEntidad){

        return Credencial.builder()
                .idCredencial(credencialEntidad.getId())
                .email(credencialEntidad.getEmail())
                .password(credencialEntidad.getPassword())
                .telefono(credencialEntidad.getTelefono())
//                .rolUsuario(credencialEntidad.getRolEntidad().getRol())
                .estado(credencialEntidad.getEstado())
                .build();

    }

    public CredencialEntidad toEntidad(Credencial credencial){

        //creamos el rol Entidad


        return CredencialEntidad.builder()
                .email(credencial.getEmail())
                .password(credencial.getPassword())
                .telefono(credencial.getTelefono())
                .estado(credencial.getEstado()) //el estado del usuario se settea por fuera del mapper

                .build();

    }


}

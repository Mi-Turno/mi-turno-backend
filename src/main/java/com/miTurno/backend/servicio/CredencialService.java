package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.CredencialesEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredencialService {


    

    //obtener todos los negocios
    //TODO PASARLO AL SERVICIO DE CREDENCIALES
    public List<UsuarioEntidad> obtenerTodosLosNegocios(){

        List<CredencialesEntidad> credencialesEntidadList= credencialesRepositorio.findAllByRolEntidad_Rol(RolUsuarioEnum.NEGOCIO);

        List<UsuarioEntidad> usuarioEntidadList = credencialesEntidadList.stream(credencialesEntidadList.get())

    }

}

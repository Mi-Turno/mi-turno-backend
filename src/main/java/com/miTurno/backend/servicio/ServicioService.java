package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.ServicioEntidad;
import com.miTurno.backend.repositorio.ServicioRepositorio;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepositorio servicioRepositorio;

    @Autowired
    public ServicioService(ServicioRepositorio servicioRepositorio) {
        this.servicioRepositorio = servicioRepositorio;
    }

    //GET all

    public List<ServicioEntidad> obtenerListadoTodosLosServicios(){
        return servicioRepositorio.findAll();
    }

    //GET x criterio

    public List<ServicioEntidad> obtenerListadoServicios(String nombreServicio, Double precio, String duracion){
        //todo: Hay que hacer un repo custom, y llamar ese metodo.
        return servicioRepositorio.findAll();
    }

    //GET ID

    //POST

    //UPDATE

    //DELETE
}

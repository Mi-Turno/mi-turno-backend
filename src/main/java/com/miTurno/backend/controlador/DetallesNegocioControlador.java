package com.miTurno.backend.controlador;

import com.miTurno.backend.mapper.DetallesNegocioMapper;
import com.miTurno.backend.modelo.DetallesNegocio;
import com.miTurno.backend.modelo.Servicio;
import com.miTurno.backend.modelo.Usuario;
import com.miTurno.backend.servicio.DetallesNegocioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/negocios")
public class DetallesNegocioControlador {

    //atributos
    DetallesNegocioService detallesNegocioService;
    DetallesNegocioMapper detallesNegocioMapper;

    //constructor

    public DetallesNegocioControlador(DetallesNegocioMapper detallesNegocioMapper, DetallesNegocioService detallesNegocioService) {
        this.detallesNegocioMapper = detallesNegocioMapper;
        this.detallesNegocioService = detallesNegocioService;
    }


    //GET todos
//    public List<DetallesNegocio> getTodosLosNegocios(){
//        detallesNegocioService.
//    }


    //GET x id
//    @GetMapping("/{id}")
//    public DetallesNegocio getNegocioPorId(@PathVariable Long id){
//
//    }

    //GET x nombre
    @GetMapping("/{nombre}")
    public DetallesNegocio getNegocioPorId(@PathVariable String nombre){

        return detallesNegocioService.obtenerDetallesNegocioPorNombre(nombre);
    }

    //GET todos servicios de negocio X nombre

    @GetMapping("/{nombre}/servicios")
    public  List<Servicio> getServiciosXNegocio(@PathVariable String nombre){
        return detallesNegocioService.
    }

//    //GET todos profesionales de negocio X nombre
//    @GetMapping("/{nombre}/profesionales")
//    public List<Usuario> getProfesionalesXNegocio(@PathVariable String nombre){
//
//    }

    //POST

    //UPDATE

    //DELETE

}

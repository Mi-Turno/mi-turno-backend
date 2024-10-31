package com.miTurno.backend.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/negocios/{idNegocio}/profesionales")
public class ProfesionalControlador {


    //GET todos los profesionales

    //GET profesional x id ("/{idProfesional}")

    //GET listado de turnos agendados del profesional ("/{idProfesional}/turnos")

    //GET listado de horarios del profesional ("/{idProfesional}/horarios")

    //GET listado de servicios que ofrece un profesional ("/{idProfesional}/servicios")

    //POST un profesional

    //POST nuevo turno a un profesional ("/{idProfesional}/turnos")

    //POST nuevo servicio a un profesional ("/{idProfesional}/servicios") (el servicio debe encontrarse en el negocio en el que esta el profesional)

    //POST nuevo horario que ofrece un profesional ("/{idProfesional}/horarios")

    //UPDATE un profesional x id ("/{idProfesional}")

    //DELETE un profesional x id ("/{idProfesional}")



}

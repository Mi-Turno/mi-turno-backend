package com.miTurno.backend.controlador;


import com.miTurno.backend.data.domain.MetodoDePagoEntidad;
import com.miTurno.backend.servicio.MetodoDePagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metodos-de-pago")
public class MetodoDePagoControlador {

    private final MetodoDePagoService metodoDePagoService;


    public MetodoDePagoControlador(MetodoDePagoService metodoDePagoService) {
        this.metodoDePagoService = metodoDePagoService;
    }
    @Operation(summary = "Obtener todos los metodos de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Metodos de pago obtenido con exito")
    })
    @GetMapping
    public List<MetodoDePagoEntidad> obtenerMetodosDePago(){
        return metodoDePagoService.getAllMetodosDePago();
    }

}

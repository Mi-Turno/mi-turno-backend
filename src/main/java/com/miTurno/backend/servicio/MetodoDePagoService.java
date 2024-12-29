package com.miTurno.backend.servicio;


import com.miTurno.backend.data.domain.MetodoDePagoEntidad;
import com.miTurno.backend.data.repositorio.MetodosDePagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoDePagoService {

    private final MetodosDePagoRepositorio metodosDePagoRepositorio;

    @Autowired
    public MetodoDePagoService(MetodosDePagoRepositorio metodosDePagoRepositorio) {
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
    }

    public List<MetodoDePagoEntidad> getAllMetodosDePago(){
        return metodosDePagoRepositorio.findAll();
    }

}

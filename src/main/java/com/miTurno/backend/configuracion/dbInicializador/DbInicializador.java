package com.miTurno.backend.configuracion.dbInicializador;

import com.miTurno.backend.entidad.DiaEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.repositorio.DiaRepositorio;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.tipos.DiasEnum;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbInicializador {
    //atributos
    private final RolRepositorio rolRepositorio;
    private final DiaRepositorio diaRepositorio;
    @Autowired
    public DbInicializador(RolRepositorio rolRepositorio, DiaRepositorio diaRepositorio) {
        //todo inicializar todos los repos
        this.rolRepositorio = rolRepositorio;
        this.diaRepositorio = diaRepositorio;
    }
    @PostConstruct
    public void init(){

        //todo init metodos de pago
        //todo init dias
        initRoles();
        initDias();
    }



    public void initRoles() {
        if (rolRepositorio.findByRol(RolUsuarioEnum.ADMIN) == null) {
            rolRepositorio.save(new RolEntidad(RolUsuarioEnum.ADMIN));
        }
        if (rolRepositorio.findByRol(RolUsuarioEnum.CLIENTE) == null) {
            rolRepositorio.save(new RolEntidad(RolUsuarioEnum.CLIENTE));
        }
        if (rolRepositorio.findByRol(RolUsuarioEnum.PROFESIONAL) == null) {
            rolRepositorio.save(new RolEntidad(RolUsuarioEnum.PROFESIONAL));
        }
        if (rolRepositorio.findByRol(RolUsuarioEnum.NEGOCIO) == null) {
            rolRepositorio.save(new RolEntidad(RolUsuarioEnum.NEGOCIO));
        }
    }

    public void initDias(){
        if(diaRepositorio.findByDia(DiasEnum.LUNES)==null){
            diaRepositorio.save(new DiaEntidad(DiasEnum.LUNES));
        }
        if(diaRepositorio.findByDia(DiasEnum.MARTES)==null){
            diaRepositorio.save(new DiaEntidad(DiasEnum.MARTES));
        }
        if(diaRepositorio.findByDia(DiasEnum.MIERCOLES)==null){
            diaRepositorio.save(new DiaEntidad(DiasEnum.MIERCOLES));
        }
        if(diaRepositorio.findByDia(DiasEnum.JUEVES)==null){
            diaRepositorio.save(new DiaEntidad(DiasEnum.JUEVES));
        }
        if(diaRepositorio.findByDia(DiasEnum.VIERNES)==null){
            diaRepositorio.save(new DiaEntidad(DiasEnum.VIERNES));
        }
        if(diaRepositorio.findByDia(DiasEnum.SABADO)==null){
            diaRepositorio.save(new DiaEntidad(DiasEnum.SABADO));
        }
        if(diaRepositorio.findByDia(DiasEnum.DOMINGO)==null){
            diaRepositorio.save(new DiaEntidad(DiasEnum.DOMINGO));
        }
    }

}

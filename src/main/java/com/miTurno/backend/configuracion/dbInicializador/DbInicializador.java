package com.miTurno.backend.configuracion.dbInicializador;

import com.miTurno.backend.DTO.Usuario;
import com.miTurno.backend.entidad.DiaEntidad;
import com.miTurno.backend.entidad.MetodoDePagoEntidad;
import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.entidad.UsuarioEntidad;
import com.miTurno.backend.mapper.UsuarioMapper;
import com.miTurno.backend.repositorio.DiaRepositorio;
import com.miTurno.backend.repositorio.MetodosDePagoRepositorio;
import com.miTurno.backend.repositorio.RolRepositorio;
import com.miTurno.backend.repositorio.UsuarioRepositorio;
import com.miTurno.backend.servicio.UsuarioService;
import com.miTurno.backend.tipos.DiasEnum;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class DbInicializador {
    //atributos
    private final RolRepositorio rolRepositorio;
    private final DiaRepositorio diaRepositorio;
    private final MetodosDePagoRepositorio metodosDePagoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public DbInicializador(RolRepositorio rolRepositorio, DiaRepositorio diaRepositorio, MetodosDePagoRepositorio metodosDePagoRepositorio, UsuarioRepositorio usuarioRepositorio, UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        //todo inicializar todos los repos
        this.rolRepositorio = rolRepositorio;
        this.diaRepositorio = diaRepositorio;
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }
    @PostConstruct
    public void init(){

       // initRoles();
        //initDias();
        //initMetodoDePagos();
        initAdmin();
    }

    public void initAdmin(){
    //validacion por si ya existe en la base de datos
       if(!usuarioRepositorio.existsById(1L)){
           Usuario admin = Usuario.builder()
                   .nombre("MiTurnoAdmin")
                   .estado(true)
                   .password("flf")
                   .email("miturno.flf@gmail.com")
                   .apellido("MiTurnoAdmin")
                   .idRolUsuario(RolUsuarioEnum.ADMIN)
                   .telefono("11111111")
                   .fechaNacimiento(LocalDate.of(2024,10,8))
                   .build();
           usuarioRepositorio.save(usuarioMapper.toEntidad(admin));
       }


    }



   /* public void initRoles() {
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
    }*/

    /*public void initDias(){
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
    }*/
    /*public void initMetodoDePagos(){
        if(metodosDePagoRepositorio.findBymetodosDePago(MetodosDePagoEnum.EFECTIVO)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.EFECTIVO));
        }
        if(metodosDePagoRepositorio.findBymetodosDePago(MetodosDePagoEnum.TRANSFERENCIA)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.TRANSFERENCIA));
        }
        if(metodosDePagoRepositorio.findBymetodosDePago(MetodosDePagoEnum.MERCADO_PAGO)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.MERCADO_PAGO));
        }
        if(metodosDePagoRepositorio.findBymetodosDePago(MetodosDePagoEnum.TARJETA_DEBITO)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.TARJETA_DEBITO));
        }
        if(metodosDePagoRepositorio.findBymetodosDePago(MetodosDePagoEnum.TARJETA_CREDITO)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.TARJETA_CREDITO));
        }
    }*/

}

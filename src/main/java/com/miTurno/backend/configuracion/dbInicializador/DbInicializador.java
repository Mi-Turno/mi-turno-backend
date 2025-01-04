package com.miTurno.backend.configuracion.dbInicializador;

import com.miTurno.backend.data.domain.DiaEntidad;
import com.miTurno.backend.data.domain.EstadoTurnoEntidad;
import com.miTurno.backend.data.domain.MetodoDePagoEntidad;
import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.data.repositorio.*;
import com.miTurno.backend.data.dtos.response.Credencial;
import com.miTurno.backend.data.dtos.response.Usuario;
import com.miTurno.backend.data.mapper.UsuarioMapper;
import com.miTurno.backend.servicio.AuthService;
import com.miTurno.backend.tipos.DiasEnum;
import com.miTurno.backend.tipos.EstadoTurnoEnum;
import com.miTurno.backend.tipos.MetodosDePagoEnum;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DbInicializador {

    //atributos
    private final RolRepositorio rolRepositorio;
    private final DiaRepositorio diaRepositorio;
    private final MetodosDePagoRepositorio metodosDePagoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final EstadoTurnoRepositorio estadoTurnoRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;


    @Autowired
    public DbInicializador(RolRepositorio rolRepositorio, DiaRepositorio diaRepositorio, MetodosDePagoRepositorio metodosDePagoRepositorio, UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper, EstadoTurnoRepositorio estadoTurnoRepositorio, PasswordEncoder passwordEncoder, AuthService authService) {
        this.rolRepositorio = rolRepositorio;
        this.diaRepositorio = diaRepositorio;
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.estadoTurnoRepositorio = estadoTurnoRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }



    @PostConstruct
    public void init(){

        initRoles();
        initDias();
        initMetodoDePagos();
        initEstadoTurno();
        initAdmin();
    }

    public void initAdmin(){
    //validacion por si ya existe en la base de datos
       if(!usuarioRepositorio.existsById(1L)){



           Credencial credencial = Credencial.builder()
                   .estado(true)
                   .password(passwordEncoder.encode("flf"))
                   .email("miturno.flf@gmail.com")
                   .telefono("11111111")
                   .vencimientoCodigoVerificacion(null)
                   .codigoVerificacion(null)
                   .build();

           Usuario admin = Usuario.builder()
                   .idUsuario(1L)
                   .nombre("MiTurnoAdmin")
                   .apellido("MiTurnoAdmin")
                   .fechaNacimiento(LocalDate.of(2024,10,8))
                   .credencial(credencial)
                   .rolUsuario(RolUsuarioEnum.ADMIN)
                   .build();

           RolEntidad rolEntidad= rolRepositorio.findByRol(RolUsuarioEnum.ADMIN);
           usuarioRepositorio.save(usuarioMapper.toEntidad(admin,rolEntidad));
       }else {
           System.out.println(usuarioRepositorio.findById(1L));
       }


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
    public void initMetodoDePagos(){
        if(metodosDePagoRepositorio.findByMetodoDePago(MetodosDePagoEnum.EFECTIVO)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.EFECTIVO));
        }
        if(metodosDePagoRepositorio.findByMetodoDePago(MetodosDePagoEnum.TRANSFERENCIA)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.TRANSFERENCIA));
        }
        if(metodosDePagoRepositorio.findByMetodoDePago(MetodosDePagoEnum.MERCADO_PAGO)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.MERCADO_PAGO));
        }
        if(metodosDePagoRepositorio.findByMetodoDePago(MetodosDePagoEnum.TARJETA_DEBITO)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.TARJETA_DEBITO));
        }
        if(metodosDePagoRepositorio.findByMetodoDePago(MetodosDePagoEnum.TARJETA_CREDITO)==null){
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.TARJETA_CREDITO));
        }
    }
    public void initEstadoTurno(){
        if(estadoTurnoRepositorio.findByEstadoTurno(EstadoTurnoEnum.LIBRE)==null){
            estadoTurnoRepositorio.save(new EstadoTurnoEntidad(EstadoTurnoEnum.LIBRE));
        }
        if(estadoTurnoRepositorio.findByEstadoTurno(EstadoTurnoEnum.RESERVADO)==null){
            estadoTurnoRepositorio.save(new EstadoTurnoEntidad(EstadoTurnoEnum.RESERVADO));
        }
        if(estadoTurnoRepositorio.findByEstadoTurno(EstadoTurnoEnum.EN_CURSO)==null){
            estadoTurnoRepositorio.save(new EstadoTurnoEntidad(EstadoTurnoEnum.EN_CURSO));
        }
        if(estadoTurnoRepositorio.findByEstadoTurno(EstadoTurnoEnum.COBRADO)==null){
            estadoTurnoRepositorio.save(new EstadoTurnoEntidad(EstadoTurnoEnum.COBRADO));
        }
        if(estadoTurnoRepositorio.findByEstadoTurno(EstadoTurnoEnum.CANCELADO)==null){
            estadoTurnoRepositorio.save(new EstadoTurnoEntidad(EstadoTurnoEnum.CANCELADO));
        }

    }
}

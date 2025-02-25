package com.miTurno.backend.configuracion.dbInicializador;

import com.miTurno.backend.data.domain.DiaEntidad;
import com.miTurno.backend.data.domain.EstadoTurnoEntidad;
import com.miTurno.backend.data.domain.MetodoDePagoEntidad;
import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.data.dtos.response.*;
import com.miTurno.backend.data.mapper.NegocioMapper;
import com.miTurno.backend.data.mapper.ProfesionalMapper;
import com.miTurno.backend.data.mapper.ServicioMapper;
import com.miTurno.backend.data.repositorio.*;
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
    private final NegocioMapper negocioMapper;



    @Autowired
    public DbInicializador(RolRepositorio rolRepositorio, DiaRepositorio diaRepositorio, MetodosDePagoRepositorio metodosDePagoRepositorio, UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper, EstadoTurnoRepositorio estadoTurnoRepositorio, PasswordEncoder passwordEncoder  , NegocioMapper negocioMapper) {
        this.rolRepositorio = rolRepositorio;
        this.diaRepositorio = diaRepositorio;
        this.metodosDePagoRepositorio = metodosDePagoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.estadoTurnoRepositorio = estadoTurnoRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.negocioMapper = negocioMapper;
    }



    @PostConstruct
    public void init(){

        initRoles();
        initDias();
        initMetodoDePagos();
        initEstadoTurno();
        initAdmin();
        initNegocio();
        //initProfesional();
        //initServicio();

    }

    public void initAdmin(){
    //validacion por si ya existe en la base de datos
       if(!usuarioRepositorio.existsById(1L)){


           Credencial credencial = Credencial.builder()
                   .estado(true)
                   .password(passwordEncoder.encode("flf"))
                   .email("miturno.flf@gmail.com")
                   .telefono("11111111")
                   .vencimientoCodigo(null)
                   .codigo(null)
                   .usuarioVerificado(true)
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

    private void initNegocio(){
        if(!usuarioRepositorio.existsById(2L)){

            ///NEGOCIO
            Credencial credencialNegocio = Credencial.builder()
                    .estado(true)
                    .password(passwordEncoder.encode("123"))
                    .email("carlos@gmail.com")
                    .telefono("2222222")
                    .vencimientoCodigo(null)
                    .codigo(null)
                    .usuarioVerificado(true)
                    .build();

            Negocio negocio = Negocio.builder()
                    .credencial(credencialNegocio)
                    .rolUsuario(RolUsuarioEnum.NEGOCIO)
                    .idUsuario(2L)
                    .nombre("Carlos")
                    .apellido("Sam")
                    .fechaNacimiento(LocalDate.of(2024,10,8))
                    .altura("1234")
                    .calle("Av.Pescadores")
                    .rubro("BarberShop")
                    .detalle("...")
                    .profesionales(null)
                    .servicios(null)
                    .build();
            
            RolEntidad rolEntidad= rolRepositorio.findByRol(RolUsuarioEnum.NEGOCIO);
            usuarioRepositorio.save(negocioMapper.toEntidad(negocio,rolEntidad));
        }else {
            System.out.println(usuarioRepositorio.findById(2L));
        }
    }
    /*public void initProfesional(){ todo fixear con el request o hacer un mapper para el profesional y el servicio
        if(!usuarioRepositorio.existsById(3L)){
            /// PROFESIONAL
            Credencial credencialProfesional = Credencial.builder()
                    .estado(true)
                    .password(passwordEncoder.encode("123"))
                    .email("roberto@gmail.com")
                    .telefono("33333333")
                    .vencimientoCodigoVerificacion(null)
                    .codigoVerificacion(null)
                    .build();

            Profesional profesional = Profesional.builder()
                    .idUsuario(3L)
                    .idNegocio(2L)
                    .nombre("Roberto")
                    .apellido("Sonoria")
                    .fechaNacimiento(LocalDate.of(2024,10,8))
                    .credencial(credencialProfesional)
                    .rolUsuario(RolUsuarioEnum.PROFESIONAL)
                    .turnosAgendados(null)
                    .build();

            RolEntidad rolEntidad= rolRepositorio.findByRol(RolUsuarioEnum.PROFESIONAL);
            usuarioRepositorio.save(profesionalMapper.toEntidad(profesional,rolEntidad));
            System.out.println(profesional);
        }
    }*/
   /* public void initServicio(){

        ///SERVICIO
        Servicio servicio = Servicio.builder()
                .idNegocio(2L)
                .idServicio(1L)
                .duracion(30)
                .nombre("Corte")
                .precio(7000.00)
                .build();
        servicioRepositorio.save(servicioMapper.toEntidad());
        System.out.println(servicio);
    }*/

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
        if(metodosDePagoRepositorio.findByMetodoDePago(MetodosDePagoEnum.OTRO)==null) {
            metodosDePagoRepositorio.save(new MetodoDePagoEntidad(MetodosDePagoEnum.OTRO));
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

package com.miTurno.backend.entidad;


import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;


@Entity
@Table(name="usuarios")
@Setter
@Getter
@Builder
public class UsuarioEntidad {
        //todo ver lo que es nulleable, unique...
    //columnas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    //@Size(min = 3, max = 50)
    @Column(name = "nombre")
    private String nombre;

    //@Size(min = 3, max = 50)
    @Column(name = "apellido")
    private String apellido;

    @Email
    @Column(name = "correo_electronico",unique = true)
    private String correoElectronico;

    @Column(name = "password",unique = true)
    private String password;

    @Column(name = "celular",unique = true)
    private String celular;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = true,columnDefinition ="DATE")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private RolUsuarioEnum rolUsuarioEnum;

    @Column(name = "estado")
    private Boolean estado;

    //constructores
    public UsuarioEntidad(){

    }

    public UsuarioEntidad(Long idUsuario,String nombre, String apellido, String correoElectronico,String password, String celular, LocalDate fechaNacimiento, RolUsuarioEnum rolUsuarioEnum, Boolean estado) {
        this.idUsuario = idUsuario;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.rolUsuarioEnum = rolUsuarioEnum;
        this.estado = estado;
    }

}

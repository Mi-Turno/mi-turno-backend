package com.miTurno.backend.entidad;


import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


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
    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "telefono",unique = true)
    private String telefono;

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

    public UsuarioEntidad(Long idUsuario,String nombre, String apellido, String email,String password, String telefono, LocalDate fechaNacimiento, RolUsuarioEnum rolUsuarioEnum, Boolean estado) {
        this.idUsuario = idUsuario;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.rolUsuarioEnum = rolUsuarioEnum;
        this.estado = estado;
    }

}

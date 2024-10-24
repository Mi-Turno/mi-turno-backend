package com.miTurno.backend.modelo;

import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class Usuario {
    //Atributos
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correoElectronico;

    private String password;
    private String celular;
    private LocalDate fechaNacimiento;
    private RolUsuarioEnum rolUsuario;
    private Boolean estado;
    //constructor

    public Usuario() {

    }

    public Usuario(Long idUsuario, String nombre, String apellido, String correoElectronico,String password, String celular, LocalDate fechaNacimiento, RolUsuarioEnum rolUsuario, Boolean estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.password = password;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.rolUsuario = rolUsuario;
        this.estado=estado;
    }

    //getters y setters

    @Override
    public String toString() {
        return "Usuario{" +
                "apellido='" + apellido + '\'' +
                ", idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", password='" + password + '\'' +
                ", celular='" + celular + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", rolUsuario=" + rolUsuario +
                ", estado=" + estado +
                '}';
    }
}

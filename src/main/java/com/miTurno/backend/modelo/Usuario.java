package com.miTurno.backend.modelo;

import com.miTurno.backend.tipos.RolUsuarioEnum;
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
    private String email;
    private String password;
    private String telefono;
    private LocalDate fechaNacimiento;
    private RolUsuarioEnum rolUsuario;
    private Boolean estado;
    //constructor

    public Usuario() {

    }

    public Usuario(Long idUsuario, String nombre, String apellido, String email, String password, String telefono, LocalDate fechaNacimiento, RolUsuarioEnum rolUsuario, Boolean estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
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
                ", correoElectronico='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", rolUsuario=" + rolUsuario +
                ", estado=" + estado +
                '}';
    }
}

package com.miTurno.backend.entidad;


import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name="usuarios")
public class UsuarioEntidad {

    //columnas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Size(min = 3, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(min = 3, max = 50)
    @Column(name = "apellido")
    private String apellido;

    @Email
    @Column(name = "correo_electronico",unique = true)
    private String correoElectronico;

    @Column(name = "celular",unique = true)
    private String celular;

    @Column(insertable = false,updatable = false,columnDefinition ="DATE")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private RolUsuarioEnum rolUsuarioEnum;

    public UsuarioEntidad(){

    }
    public UsuarioEntidad(Long idUsuario, String nombre, String apellido, String correoElectronico, String celular, LocalDate fechaNacimiento, RolUsuarioEnum rolUsuarioEnum) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.rolUsuarioEnum = rolUsuarioEnum;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public RolUsuarioEnum getRolUsuarioEnum() {
        return rolUsuarioEnum;
    }

    public void setRolUsuarioEnum(RolUsuarioEnum rolUsuarioEnum) {
        this.rolUsuarioEnum = rolUsuarioEnum;
    }
}

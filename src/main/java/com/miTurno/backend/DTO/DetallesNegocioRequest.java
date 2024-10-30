package com.miTurno.backend.DTO;


import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "Requisitos para la creacion de un negocio")
@Builder
@Getter
@Setter
public class DetallesNegocioRequest {

    //usuario

    @Schema(description = "El nombre del usuario", example = "Juan")
    //@Size(min = 3, max = 50)
    private String nombre;

    @Schema(description = "El apellido del usuario", example = "Perez")
    //@Size(min = 3, max = 50)
    private String apellido;

    @Schema(description = "El Email del usuario", example = "Juan@example.com")
    @Email
    private String email;

    //@Size(min = 6, max = 10)
    @Schema(description = "La contraseña del usuario", example = "example1")
    private String password;

    @Schema(description = "El telefono del usuario", example = "12345678")
    private String telefono;

    @Schema(description = "La fecha del usuario", example = "2004-10-10")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)

    @Schema(description = "El rol del usuario", example = "NEGOCIO")
    private RolEntidad rolEntidad;

    //detalles negocio
    @Schema(description = "rubro del negocio", example = "Peluqueria")
    private String rubro;

    @Schema(description = "calle del negocio", example = "San Juan")
    private String calle;

    @Schema(description = "altura del negocio", example = "3241")
    private String altura;

    @Schema(description = "detalles del negocio", example = "Departamento, piso 3")
    private String detalle;

    public DetallesNegocioRequest(String nombre, String apellido, String email, String password, String telefono, LocalDate fechaNacimiento, RolEntidad rolEntidad, String rubro, String calle, String altura, String detalle) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.rolEntidad = rolEntidad;
        this.rubro = rubro;
        this.calle = calle;
        this.altura = altura;
        this.detalle = detalle;
    }
    public DetallesNegocioRequest(){

    }
}

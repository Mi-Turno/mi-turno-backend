package com.miTurno.backend.DTO;

import com.miTurno.backend.tipos.RolUsuarioEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
public class Usuario {
    //Atributos
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Credencial credencial;

    //constructor

    public Usuario() {}

}

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
    private String email;
    private String password;
    private String telefono;
    private LocalDate fechaNacimiento;
    private RolUsuarioEnum idRolUsuario;//Un id no tiene que ser si o si un Long si no que tambien puede ser un String y se parsea a lo que yo quiera
    private Boolean estado;


    //constructor

    public Usuario() {}

}

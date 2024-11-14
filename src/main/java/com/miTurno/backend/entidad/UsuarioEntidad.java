package com.miTurno.backend.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="usuarios")
@Setter
@Getter
@SuperBuilder
public class UsuarioEntidad {

    //columnas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Size(min = 3, max = 50)
    @Column(name = "nombre")
    private String nombre;

    //@Size(min = 3, max = 50)
    @Column(name = "apellido")
    private String apellido;


    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = true,columnDefinition ="DATE")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CredencialesEntidad credenciales; // Relación con Credenciales

    //constructores
    public UsuarioEntidad(){
    super();
    }

    @Override
    public String toString() {
        return "UsuarioEntidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", credenciales=" + credenciales +
                '}';
    }
}

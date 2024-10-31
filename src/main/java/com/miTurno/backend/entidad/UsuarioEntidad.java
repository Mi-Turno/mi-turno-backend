package com.miTurno.backend.entidad;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="usuarios")
@Setter
@Getter
@Builder
public class UsuarioEntidad {

    //columnas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    //@Size(min = 3, max = 50)
    @Column(name = "nombre")
    private String nombre;

    //@Size(min = 3, max = 50)
    @Column(name = "apellido")
    private String apellido;

//    @Email
//    @Column(name = "email",unique = true)
//    private String email;
//
//    @Column(name = "password")
//    private String password;


    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = true,columnDefinition ="DATE")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credenciales", nullable = false) // Clave foránea a CredencialesEntidad
    private CredencialesEntidad credenciales; // Relación con Credenciales



//    @ManyToOne(fetch = FetchType.EAGER) // EAGER para cargar el rol junto con el usuario
//    @JoinColumn(name = "id_rol", nullable = false) // Define la clave foránea a RolEntidad
//    private RolEntidad rolEntidad;
//
//    @Column(name = "estado")
//    private Boolean estado;

    //constructores
    public UsuarioEntidad(){

    }


}

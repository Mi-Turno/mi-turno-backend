package com.miTurno.backend.data.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="usuarios")
@Setter
@Getter
@SuperBuilder
public class UsuarioEntidad implements UserDetails {

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
    @Column(columnDefinition ="DATE")
    private LocalDate fechaNacimiento;//(YYYY-MM-DD)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credencial_id") //el name es el nombre de la columna que yo le voy a poner
    private CredencialEntidad credencial; // Relación con Credenciales

    //todo Cambiar de rolentidad many to one a many to many

    // @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    //    @JoinTable(
    //            name = "credenciales_roles",
    //           joinColumns = @JoinColumn(name = "credencial_id"),
    //           inverseJoinColumns = @JoinColumn(name = "rol_id")
    //    )
    @ManyToOne(fetch = FetchType.EAGER) // EAGER para cargar el rol junto con el usuario
    @JoinColumn(name = "rol", nullable = false) // Define la clave foránea a RolEntidad
    private RolEntidad rolEntidad;


    private String fotoPerfil;

    //public void agregarRol(RolEntity rol) {
    //        this.roles.add(rol);
    //    }

    //constructores
    public UsuarioEntidad(){
    super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rolEntidad.getRol().name()));
    }

    @Override
    public String getPassword() {
        return credencial.getPassword();
    }

    @Override
    public String getUsername() {
        return credencial.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

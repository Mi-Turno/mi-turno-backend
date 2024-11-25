package com.miTurno.backend.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miTurno.backend.tipos.RolUsuarioEnum;
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
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rolEntidad.getRol().toString()));
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return "";
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return "";
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credencial_id") //el name es el nombre de la columna que yo le voy a poner
    private CredencialEntidad credencial; // Relación con Credenciales

    @ManyToOne(fetch = FetchType.EAGER) // EAGER para cargar el rol junto con el usuario
    @JoinColumn(name = "rol", nullable = false) // Define la clave foránea a RolEntidad
    private RolEntidad rolEntidad;

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
                ", credenciales=" + credencial +
                '}';
    }
}

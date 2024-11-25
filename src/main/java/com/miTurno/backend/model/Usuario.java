package com.miTurno.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class Usuario implements UserDetails {
    //Atributos
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Credencial credencial;
    private RolUsuarioEnum rolUsuario;
    //constructor

    public Usuario() {}
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rolUsuario.toString()));
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
}

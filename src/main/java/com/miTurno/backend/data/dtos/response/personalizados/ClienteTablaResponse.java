package com.miTurno.backend.data.dtos.response.personalizados;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class ClienteTablaResponse {
/*id: string;
  nombre: string;
  apellido: string;
  correo: string;
  telefono: string;
  rol: string;
  fechaNacimiento:string;
  estado: boolean*/
    /**
     * Los atributos tienen estos nombres ya que se reflejan asi en los nombres de la tabla (Cambiar uno equivale a cambiar la tabla )
     * */
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String rol;
    private LocalDate fechaNacimiento;
    private Boolean estado;

    public ClienteTablaResponse() {
    }

    public ClienteTablaResponse(Long id, String nombre, String apellido, String correo, String telefono, String rol, LocalDate fechaNacimiento, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ClienteTablaResponse{" +
                "idCliente=" + id +
                ", nombreCliente='" + nombre + '\'' +
                ", apellidoCliente='" + apellido + '\'' +
                ", emailCliente='" + correo + '\'' +
                ", telefonoCliente='" + telefono + '\'' +
                ", rolCliente='" + rol + '\'' +
                ", fechaNacimientoCliente=" + fechaNacimiento +
                ", estadoCliente=" + estado +
                '}';
    }
}

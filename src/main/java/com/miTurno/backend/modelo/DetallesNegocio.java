package com.miTurno.backend.modelo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Builder
public class DetallesNegocio {
    //atributos
    private Long idDetallesNegocio; //pk
    private String rubro;
    private String nombreNegocio; //unique
    private Long idNegocio;  //fk a tabla usuarios

    //constructor
    public DetallesNegocio(Long idDetallesNegocio, Long idNegocio, String nombreNegocio, String rubro) {
        this.idDetallesNegocio = idDetallesNegocio;
        this.idNegocio = idNegocio;
        this.nombreNegocio = nombreNegocio;
        this.rubro = rubro;
    }

    //metodos


    @Override
    public String toString() {
        return "DetallesNegocio{" +
                "idDetallesNegocio=" + idDetallesNegocio +
                ", idNegocio=" + idNegocio +
                ", nombreNegocio='" + nombreNegocio + '\'' +
                ", rubro='" + rubro + '\'' +
                '}';
    }
}

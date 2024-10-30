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
    private String calle;
    private String altura;
    private String detalle;


    //constructor

    public DetallesNegocio(Long idDetallesNegocio, String rubro, String calle, String altura, String detalle) {
        this.idDetallesNegocio = idDetallesNegocio;
        this.rubro = rubro;
        this.calle = calle;
        this.altura = altura;
        this.detalle = detalle;
    }


    //metodos


    @Override
    public String toString() {
        return "DetallesNegocio{" +
                "altura='" + altura + '\'' +
                ", idDetallesNegocio=" + idDetallesNegocio +
                ", rubro='" + rubro + '\'' +
                ", calle='" + calle + '\'' +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}

package com.miTurno.backend.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Schema(description = "Requisitos para la creacion de un negocio")
@SuperBuilder
@Getter
@Setter
public class NegocioRequest extends UsuarioRequest{

    //detalles negocio
    @Schema(description = "rubro del negocio", example = "Peluqueria")
    private String rubro;

    @Schema(description = "calle del negocio", example = "San Juan")
    private String calle;

    @Schema(description = "altura del negocio", example = "3241")
    private String altura;

    @Schema(description = "detalles del negocio", example = "Departamento, piso 3")
    private String detalle;


    public NegocioRequest(){
    super();
    }
}

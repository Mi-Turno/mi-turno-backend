package com.miTurno.backend.entidad;


import com.miTurno.backend.tipos.DiasEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "dias")
public class DiaEntidad {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_dias",unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private DiasEnum dia;
    //constructores
    public DiaEntidad(Long id, DiasEnum diaEnum) {
        this.id = id;
        this.dia = diaEnum;
    }
    public DiaEntidad(DiasEnum dia){
        this.dia=dia;
    }
    public DiaEntidad(){

    }
}

package com.miTurno.backend.servicio;

import com.miTurno.backend.entidad.RolEntidad;
import com.miTurno.backend.excepcion.*;
import com.miTurno.backend.repositorio.*;
import com.miTurno.backend.request.NegocioRequest;
import com.miTurno.backend.entidad.NegocioEntidad;
import com.miTurno.backend.mapper.NegocioMapper;
import com.miTurno.backend.DTO.Negocio;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NegocioService {

    //atributos
    private final NegocioRepositorio negocioRepositorio;
    private final NegocioMapper negocioMapper;
    private final RolRepositorio rolRepositorio;

    //constructores
    @Autowired
    public NegocioService(NegocioMapper negocioMapper, NegocioRepositorio negocioRepositorio, RolRepositorio rolRepositorio) {
        this.negocioMapper = negocioMapper;
        this.negocioRepositorio = negocioRepositorio;
        this.rolRepositorio = rolRepositorio;
    }

    //metodos

    //GET all negocios
    public List<Negocio> listarTodosLosNegocios(){
        return negocioMapper.toModelList(negocioRepositorio.findAll()) ;
    }



    //GET negocio x id
    public Negocio obtenerNegocioPorId(Long idNegocio){
        return negocioMapper.toModel(negocioRepositorio.findById(idNegocio).orElseThrow(()-> new UsuarioNoExistenteException(idNegocio)));
    }

    //obtener negocio x nombre
    public Negocio obtenerNegocioPorNombre(String nombre) throws NombreNoExisteException {
       return negocioMapper.toModel(negocioRepositorio.getNegocioEntidadByNombreIgnoreCase(nombre).orElseThrow(()->new NombreNoExisteException(nombre)));
    }


    //POST negocio
    public Negocio crearUnNegocio(NegocioRequest negocioRequest)
            throws NombreNegocioYaExisteException, RolIncorrectoException,EmailYaExisteException, TelefonoYaExisteException {


        String nombreNegocio = negocioRequest.getNombre();

        if (negocioRequest.getRolUsuario() != RolUsuarioEnum.NEGOCIO) {
            throw new RolIncorrectoException(RolUsuarioEnum.NEGOCIO, negocioRequest.getRolUsuario());
        }
        //todo: antes había un negocioRequest.getRolUsuario() - En caso de que solucionen algo y esto les de problema
        if (negocioRepositorio.existsByNombreAndRolEntidad_Rol(nombreNegocio,negocioRequest.getRolUsuario())) {
            throw new NombreNegocioYaExisteException(nombreNegocio);
        }
        if(negocioRepositorio.existsByCredencial_Email(negocioRequest.getCredencial().getEmail())){
            throw new EmailYaExisteException(negocioRequest.getCredencial().getEmail());
        }
        if(negocioRepositorio.existsByCredencial_Telefono(negocioRequest.getCredencial().getTelefono())){
            throw new TelefonoYaExisteException(negocioRequest.getCredencial().getTelefono());
        }

        System.out.println("Paso todas las verificaciones ");

        RolEntidad rolEntidad = rolRepositorio.findByRol(negocioRequest.getRolUsuario());

        Negocio negocio= negocioMapper.toModel(negocioRequest);

        return negocioMapper.toModel(negocioRepositorio.save(negocioMapper.toEntidad(negocio,rolEntidad)));
    }

    //GET id negocio x nombre negocio
    public Long obtenerIdNegocioPorNombreNegocio(String nombreNegocio){

        NegocioEntidad negocioEntidad= negocioRepositorio.getNegocioEntidadByNombreIgnoreCase(nombreNegocio).orElseThrow(()-> new NombreNoExisteException("Nombre negocio"));
        return negocioEntidad.getId();
    }

    //GET listado de negocios x nombre parecido
    public List<NegocioEntidad> obtenerListadoDeNegociosConNombreAproximado(String nombreNegocio){

        return negocioRepositorio.getNegocioEntidadsByNombreLikeIgnoreCase(nombreNegocio);
    }


}

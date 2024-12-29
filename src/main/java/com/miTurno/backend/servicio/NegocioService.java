package com.miTurno.backend.servicio;
import com.miTurno.backend.data.domain.RolEntidad;
import com.miTurno.backend.data.repositorio.NegocioRepositorio;
import com.miTurno.backend.data.repositorio.RolRepositorio;
import com.miTurno.backend.excepciones.*;
import com.miTurno.backend.data.dtos.request.NegocioRequest;
import com.miTurno.backend.data.domain.NegocioEntidad;
import com.miTurno.backend.data.mapper.NegocioMapper;
import com.miTurno.backend.data.dtos.model.Negocio;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
    public Negocio obtenerNegocioPorId(Long idNegocio) throws EntityNotFoundException{

        return negocioMapper.toModel(negocioRepositorio.findById(idNegocio)
                .orElseThrow(()-> new EntityNotFoundException("Negocio con id: "+ idNegocio+" no encontrado.")));
    }

    //obtener negocio x nombre
    public Negocio obtenerNegocioPorNombre(String nombre) throws EntityNotFoundException {

       return negocioMapper.toModel(negocioRepositorio.getNegocioEntidadByNombreIgnoreCase(nombre)
               .orElseThrow(()->new EntityNotFoundException("El negocio con el nombre: "+nombre+" no fue encontrado.")));
    }


    //POST negocio
    public Negocio crearUnNegocio(NegocioRequest negocioRequest)
            throws RolIncorrectoException, EntityExistsException {


        String nombreNegocio = negocioRequest.getNombre();

        if (negocioRequest.getRolUsuario() != RolUsuarioEnum.NEGOCIO) {
            throw new RolIncorrectoException(RolUsuarioEnum.NEGOCIO, negocioRequest.getRolUsuario());
        }
        //todo: antes había un negocioRequest.getRolUsuario() - En caso de que solucionen algo y esto les de problema

        if (negocioRepositorio.existsByNombreAndRolEntidad_Rol(nombreNegocio,negocioRequest.getRolUsuario())) {
            throw new EntityExistsException("El negocio cono el nombre: "+nombreNegocio+" ya existe.");
        }
        if(negocioRepositorio.existsByCredencial_Email(negocioRequest.getCredencial().getEmail())){
            throw new EntityExistsException("El negocio con el email: "+negocioRequest.getCredencial().getEmail()+" ya existe");
        }
        if(negocioRepositorio.existsByCredencial_Telefono(negocioRequest.getCredencial().getTelefono())){
            throw new EntityExistsException("El negocio con el telefono: "+negocioRequest.getCredencial().getTelefono()+" ya existe.");
        }

        RolEntidad rolEntidad = rolRepositorio.findByRol(negocioRequest.getRolUsuario());

        Negocio negocio= negocioMapper.toModel(negocioRequest);

        return negocioMapper.toModel(negocioRepositorio.save(negocioMapper.toEntidad(negocio,rolEntidad)));
    }

    //GET id negocio x nombre negocio
    public Long obtenerIdNegocioPorNombreNegocio(String nombreNegocio) throws EntityNotFoundException{

        NegocioEntidad negocioEntidad= negocioRepositorio.getNegocioEntidadByNombreIgnoreCase(nombreNegocio)
                .orElseThrow(()-> new EntityNotFoundException("El negocio con el nombre: "+nombreNegocio+" no fue encontrado."));

        return negocioEntidad.getId();
    }

    //GET listado de negocios x nombre parecido
    public List<NegocioEntidad> obtenerListadoDeNegociosConNombreAproximado(String nombreNegocio){

        return negocioRepositorio.getNegocioEntidadsByNombreLikeIgnoreCase(nombreNegocio);
    }


}

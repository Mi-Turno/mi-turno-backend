package com.miTurno.backend.servicio;

import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.repositorio.UsuarioRepositorio;
import com.miTurno.backend.tipos.EntidadEnum;
import com.miTurno.backend.tipos.RolUsuarioEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ArchivosService {

    private final UsuarioRepositorio usuarioRepositorio;

    public ArchivosService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Resource obtenerFotoPerfil(Long idUsuario) throws MalformedURLException, FileNotFoundException {
        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(()-> new EntityNotFoundException("El usuario con el id "+idUsuario+" no existe."));

        if (usuarioEntidad.getFotoPerfil() == null){
            throw new FileNotFoundException("El usuario no tiene foto de perfil");
        }


        Path imagenPath = Path.of(usuarioEntidad.getFotoPerfil());
        return new UrlResource(imagenPath.toUri());

    }

    public Boolean eliminarFotoPerfilUsuario(Long idUsuario) throws IOException {
        boolean flag=false;

        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(()-> new EntityNotFoundException("El usuario con el id "+idUsuario+" no existe."));

        if (usuarioEntidad.getFotoPerfil() == null){
            throw new FileNotFoundException("El usuario no tiene foto de perfil");
        }

        Path archivoABorrar= Path.of(usuarioEntidad.getFotoPerfil());

        if (!Files.exists(archivoABorrar)){
            return flag;
        }

        flag= Files.deleteIfExists(archivoABorrar);
        usuarioEntidad.setFotoPerfil(null);
        usuarioRepositorio.save(usuarioEntidad);

        return flag;
    }

    public Boolean guardarFotoPerfilUsuario(Long id, MultipartFile archivo, EntidadEnum entidadEnum) throws IOException,EntityNotFoundException {
        boolean flag=false;

        switch (entidadEnum){
            case USUARIO -> {
                flag = logicaGuardarArchivoUsuario(id,archivo);
            }
            case SERVICIOS -> {

            }
            case null, default -> {
                //arrojo error entidad no existe
            }
        }

        return flag;
    }

    private Boolean logicaGuardarArchivoUsuario(Long id, MultipartFile archivo) throws IOException {
        boolean flag=false;
        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("El usuario con el id "+id+" no existe."));

        if (archivo.isEmpty() || archivo.getOriginalFilename() == null){
            return flag;
        }


        Path rutaArchivosCliente = obtenerDirectorioCliente(id);

        if (usuarioEntidad.getRolEntidad().getRol() == RolUsuarioEnum.NEGOCIO){
            Path rutaArchivosNegocio= obtenerDirectorioServicios(rutaArchivosCliente);
        }

        flag = guardarUnArchivoEnUnUsuario(archivo,rutaArchivosCliente,usuarioEntidad);
        return flag;
    }

   private Path obtenerDirectorioServicios(Path rutaArchivosNegocio) throws IOException {
       //ruta donde se guardaran los archivos del cliente, el nombre del directorio sera el id del usuario
       Path rutaArchivosServicios = rutaArchivosNegocio.resolve("servicios");

       //si el directorio no tiene una carpeta creada, se la creamos
       if (!Files.exists(rutaArchivosServicios)){
           Files.createDirectories(rutaArchivosServicios);
       }
       return rutaArchivosServicios;
   }


    private boolean guardarUnArchivoEnUnUsuario(MultipartFile archivo, Path rutaEnDondeSeGuardara, UsuarioEntidad usuarioEntidad) throws IOException {
        boolean flag=false;

        String nombreNuevoArchivo = archivo.getOriginalFilename();

        if (nombreNuevoArchivo == null){
            return flag;
        }

        //si tiene foto de perfil
        if (usuarioEntidad.getFotoPerfil() != null){
            eliminarFotoPerfilUsuario(usuarioEntidad.getId());
        }

        Path rutaDelNuevoArchivo = rutaEnDondeSeGuardara.resolve(nombreNuevoArchivo);

        Files.copy(archivo.getInputStream(),rutaDelNuevoArchivo, StandardCopyOption.REPLACE_EXISTING);

        if (Files.exists(rutaDelNuevoArchivo)){
            rutaDelNuevoArchivo = rutaDelNuevoArchivo.normalize();

            usuarioEntidad.setFotoPerfil(String.valueOf(rutaDelNuevoArchivo));
            usuarioRepositorio.save(usuarioEntidad);
            flag=true;
        }
        return flag;
    }

    private Path obtenerDirectorioCliente(Long idUsuario) throws IOException {
        Path rutaMiTurno = obtenerDirectorioMiTurno();

        //ruta donde se guardaran los archivos del cliente, el nombre del directorio sera el id del usuario
        Path rutaArchivosCliente = rutaMiTurno.resolve(String.valueOf(idUsuario));

        //si el cliente no tiene una carpeta creada, se la creamos
        if (!Files.exists(rutaArchivosCliente)){
            Files.createDirectories(rutaArchivosCliente);
        }
        return rutaArchivosCliente;
    }

    private Path obtenerDirectorioMiTurno() throws IOException {
        //obtenemos la ruta donde guardaremos los archivos. Esta es una ruta temporal, donde se borrara cuando el sistema se apague.
        Path rutaArchivos = Path.of(System.getProperty("java.io.tmpdir"),"fotos-mi-turno");

        //Si el directorio fotos-mi-turno no existe, lo creamos
        if(!Files.exists(rutaArchivos)){
            Files.createDirectories(rutaArchivos);
        }
        return rutaArchivos;
    }


}

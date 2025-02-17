package com.miTurno.backend.servicio;

import com.miTurno.backend.data.domain.UsuarioEntidad;
import com.miTurno.backend.data.repositorio.UsuarioRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ArchivosService {

    private final UsuarioRepositorio usuarioRepositorio;

    public ArchivosService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Boolean eliminarFotoPerfilUsuario(Long idUsuario) throws IOException {
        boolean flag=false;

        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(()-> new EntityNotFoundException("El usuario con el id "+idUsuario+" no existe."));


        Path archivoABorrar= Path.of(usuarioEntidad.getFotoPerfil());

        if (!Files.exists(archivoABorrar)){
            return flag;
        }

        flag= Files.deleteIfExists(archivoABorrar);
        usuarioEntidad.setFotoPerfil(null);
        usuarioRepositorio.save(usuarioEntidad);






        return flag;
    }

    public Boolean guardarFotoPerfilUsuario(Long idUsuario, MultipartFile archivo) throws IOException,EntityNotFoundException {
        boolean flag=false;

        UsuarioEntidad usuarioEntidad = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(()-> new EntityNotFoundException("El usuario con el id "+idUsuario+" no existe."));

        if (archivo.isEmpty() || archivo.getOriginalFilename() == null){
            return flag;
        }

        Path rutaArchivosCliente = obtenerDirectorioCliente(idUsuario);

        flag = guardarUnArchivoEnUnUsuario(archivo,rutaArchivosCliente,usuarioEntidad);


        return flag;
    }

    private boolean guardarUnArchivoEnUnUsuario(MultipartFile archivo, Path rutaEnDondeSeGuardara, UsuarioEntidad usuarioEntidad) throws IOException {
        boolean flag=false;

        String nombreNuevoArchivo = archivo.getOriginalFilename();

        if (nombreNuevoArchivo == null){
            return flag;
        }

        //si tiene foto de perfil
//        if (!usuarioEntidad.getFotoPerfil().isEmpty() ){
//            eliminarFotoPerfilUsuario(usuarioEntidad.getId());
//        }

        Path rutaDelNuevoArchivo = rutaEnDondeSeGuardara.resolve(nombreNuevoArchivo);

        Files.copy(archivo.getInputStream(),rutaDelNuevoArchivo, StandardCopyOption.REPLACE_EXISTING);


        if (Files.exists(rutaEnDondeSeGuardara)){
            usuarioEntidad.setFotoPerfil(rutaDelNuevoArchivo.toString());
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

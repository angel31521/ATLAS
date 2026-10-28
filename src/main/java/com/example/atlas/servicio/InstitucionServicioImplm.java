package com.example.atlas.servicio;

import org.springframework.stereotype.Service;

import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.InstitucionModel;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.repositorio.InstitucionRepositorio;
import com.example.atlas.repositorio.UsuarioRepositorio;

import jakarta.transaction.Transactional;


@Service 
public class InstitucionServicioImplm implements InstitucionServicio {


        
    private final InstitucionRepositorio institucionRepositorio;
    private final UsuarioRepositorio usuarioRepositorio ;

    public  InstitucionServicioImplm(InstitucionRepositorio institucionRepositorio,
                                    UsuarioRepositorio usuarioRepositorio ){

        this.institucionRepositorio = institucionRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }


    @Override
    @Transactional 
    public InstitucionModel crearInstitucion(String nombre, Long coordinadorId) {

                UsuarioModel coordinador = usuarioRepositorio
                .findById(coordinadorId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Coordinador no encontrado"));


        if (coordinador.getRol() != Rol.COORDINADOR) {
            throw new IllegalArgumentException(
                    "El usuario no es coordinador");
        }

        if (coordinador.getInstitucion() != null) {
            throw new IllegalStateException(
                    "El coordinador ya tiene una institución");
        }


        InstitucionModel institucion = new InstitucionModel();
        institucion.setNombre(nombre);

        InstitucionModel institucionGuardada =
                institucionRepositorio.save(institucion);

        coordinador.setInstitucion(institucionGuardada);
        usuarioRepositorio.save(coordinador);

        return institucionGuardada;

    }






}

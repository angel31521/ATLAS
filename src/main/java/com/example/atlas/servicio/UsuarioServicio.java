package com.example.atlas.servicio;

import org.springframework.stereotype.Service;
import com.example.atlas.repositorio.UsuarioRepositorio;


@Service 
public class UsuarioServicio {

 private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio){

        this.usuarioRepositorio = usuarioRepositorio;

    }


    



}

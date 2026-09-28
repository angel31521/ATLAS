package com.example.atlas.servicio;

import org.springframework.stereotype.Service;
import com.example.atlas.repositorio.EstudianteRepositorio;

@Service 
public class EstudianteServicio {

    private final EstudianteRepositorio estudianteRepositorio;


    public EstudianteServicio(EstudianteRepositorio estudianteRepositorio){

        this.estudianteRepositorio = estudianteRepositorio;

    }



}

package com.example.atlas.servicio;

import org.springframework.stereotype.Service;
import com.example.atlas.repositorio.CursoRepositorio;


@Service 
public class CursoServicio {

private final CursoRepositorio cursoRepositorio;

public CursoServicio(CursoRepositorio cursoRepositorio){

    this.cursoRepositorio = cursoRepositorio;

}


}

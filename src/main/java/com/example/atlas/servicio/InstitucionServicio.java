package com.example.atlas.servicio;

import org.springframework.stereotype.Service;
import com.example.atlas.repositorio.InstitucionRepositorio;


@Service 
public class InstitucionServicio {


private InstitucionRepositorio institucionRepositorio;

public  InstitucionServicio(InstitucionRepositorio institucionRepositorio){

    this.institucionRepositorio = institucionRepositorio;
}


}

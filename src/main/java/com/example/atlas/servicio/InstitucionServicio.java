package com.example.atlas.servicio;

import org.springframework.stereotype.Service;

import com.example.atlas.modelo.InstitucionModel;



@Service 
public interface InstitucionServicio {

       InstitucionModel crearInstitucion(String nombre, Long coordinadorId);


}

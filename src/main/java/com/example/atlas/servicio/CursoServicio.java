package com.example.atlas.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.atlas.modelo.CursoModel;



@Service 
public interface CursoServicio  {


    void guardarCurso(CursoModel curso, Long coordinadorId);

    List<CursoModel> listarCursosPorCoordinador(Long coordinadorId);

}

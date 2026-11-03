package com.example.atlas.servicio;

import java.util.List;

import com.example.atlas.modelo.EstudianteModel;

public interface EstudianteServicio {

    List<EstudianteModel> listarPorCoordinador(Long coordinadorId);

    void guardar(EstudianteModel estudiante, Long cursoId, Long coordinadorId);

    EstudianteModel buscarDelCoordinador(Long id, Long coordinadorId);

    void actualizar(Long id, EstudianteModel datos, Long coordinadorId);
}
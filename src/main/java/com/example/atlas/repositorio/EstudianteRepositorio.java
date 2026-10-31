package com.example.atlas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.atlas.modelo.CursoModel;
import com.example.atlas.modelo.EstudianteModel;

public interface EstudianteRepositorio extends JpaRepository<EstudianteModel, Long> {

    // Todos los estudiantes de una institución (estudiante -> curso -> institución)
    List<EstudianteModel> findByCursoInstitucionId(Long institucionId);

    // Estudiantes de un curso concreto
    List<EstudianteModel> findByCursoId(Long cursoId);

    List<EstudianteModel> findByCursoIn(List<CursoModel> cursos);

    List<EstudianteModel> findByCursoIdIn(List<Long> cursoIds);
}

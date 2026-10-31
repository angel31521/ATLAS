package com.example.atlas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.atlas.modelo.CursoDocenteModel;

public interface CursoDocenteRepositorio extends JpaRepository<CursoDocenteModel, Long> {

    List<CursoDocenteModel> findByDocenteId(Long docenteId);

    List<CursoDocenteModel> findByDocenteIdAndCursoInstitucionId(Long docenteId, Long institucionId);

    boolean existsByCursoIdAndDocenteIdAndCargoIgnoreCase(Long cursoId, Long docenteId, String cargo);
}
package com.example.atlas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atlas.modelo.CursoModel;

public interface CursoRepositorio extends JpaRepository<CursoModel, Long> {

    List<CursoModel> findByInstitucionId(Long institucionId);



}

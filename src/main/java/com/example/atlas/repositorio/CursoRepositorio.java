package com.example.atlas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atlas.modelo.CursoModel;

public interface CursoRepositorio extends JpaRepository<CursoModel, Long> {

}

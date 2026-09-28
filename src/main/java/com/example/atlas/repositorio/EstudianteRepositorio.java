package com.example.atlas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atlas.modelo.EstudianteModel;

public interface EstudianteRepositorio extends JpaRepository<EstudianteModel, Long> {

}

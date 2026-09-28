package com.example.atlas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atlas.modelo.InstitucionModel;

public interface InstitucionRepositorio extends JpaRepository <InstitucionModel, Long> {

}

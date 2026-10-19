package com.example.atlas.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.atlas.modelo.UsuarioModel;


public interface UsuarioRepositorio extends CrudRepository<UsuarioModel, Long> {

    
} 
package com.example.atlas.repositorio;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atlas.modelo.UsuarioModel;


public interface UsuarioRepositorio extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByCorreo(String correo);



} 
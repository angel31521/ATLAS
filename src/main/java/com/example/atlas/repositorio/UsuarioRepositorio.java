package com.example.atlas.repositorio;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atlas.modelo.UsuarioModel;

import java.util.List;
import com.example.atlas.enums.Rol;


public interface UsuarioRepositorio extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByCorreo(String correo);

    boolean existsByCorreo(String correo);

    Optional<UsuarioModel> findByCorreoIgnoreCase(String correo);

    List<UsuarioModel> findByInstitucionIdAndRol(Long institucionId, Rol rol);

} 
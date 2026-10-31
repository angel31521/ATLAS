package com.example.atlas.servicio;

import com.example.atlas.modelo.UsuarioModel;

public interface UsuarioServicio {

    void registrarUsuario(UsuarioModel usuario);

    UsuarioModel validarLogin(String correo, char[] contraseña);

    UsuarioModel buscarPorId(Long id);

    boolean tieneInstitucion(Long usuarioId);
}
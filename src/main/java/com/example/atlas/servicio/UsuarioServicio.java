package com.example.atlas.servicio;

import org.springframework.stereotype.Service;
import com.example.atlas.modelo.UsuarioModel;



@Service 
public interface UsuarioServicio {

     public UsuarioModel crearUsuarioRol(String tipo);

     public void registrarUsuario(UsuarioModel usuario);

     public UsuarioModel validarLogin(String correo, char[]contraseña);

     UsuarioModel buscarPorId(Long id);

     boolean tieneInstitucion(Long usuarioId);


}

package com.example.atlas.servicio;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImplm implements UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicioImplm(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Registra el usuario validando rol y correo repetido.
    // El rol llega del formulario; como el enum Rol solo tiene COORDINADOR y DOCENTE,
    // basta con comprobar que no venga vacío.
    @Override
    public void registrarUsuario(UsuarioModel usuario) {

        if (usuario.getRol() == null) {
            throw new IllegalArgumentException("Selecciona si eres coordinador o docente.");
        }

        String correo = usuario.getCorreo() == null ? "" : usuario.getCorreo().trim().toLowerCase();
        if (correo.isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }

        if (usuarioRepositorio.existsByCorreo(correo)) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con este correo.");
        }

        usuario.setId(null);            // siempre se crea uno nuevo
        usuario.setCorreo(correo);
        usuario.setInstitucion(null);   // el formulario no debe poder asignar institución

        usuarioRepositorio.save(usuario);
    }

    // Busca por correo y compara la contraseña
    // revisar como manejar la contraseña de forma segura
    @Override
    public UsuarioModel validarLogin(String correo, char[] contraseña) {

        String correoLimpio = correo == null ? "" : correo.trim().toLowerCase();

        UsuarioModel usuario = usuarioRepositorio.findByCorreo(correoLimpio).orElse(null);

        if (usuario == null) {
            return null;
        }

        if (!Arrays.equals(usuario.getContraseña(), contraseña)) {
            return null;
        }

        return usuario;
    }

    @Override
    public UsuarioModel buscarPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Override
    public boolean tieneInstitucion(Long usuarioId) {
        UsuarioModel usuario = buscarPorId(usuarioId);
        return usuario != null && usuario.getInstitucion() != null;
    }
}
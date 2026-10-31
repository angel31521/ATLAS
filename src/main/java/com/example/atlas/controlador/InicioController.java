package com.example.atlas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class InicioController {

    private final UsuarioServicio usuarioServicio;

    public InicioController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/")
    public String defaultRedirection() {
        return "redirect:/login";
    }

    // ---------- LOGIN (único para ambos roles) ----------

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "email") String correo,
                        @RequestParam(name = "password") char[] contraseña,
                        Model model,
                        HttpSession session) {

        UsuarioModel usuario = usuarioServicio.validarLogin(correo, contraseña);

        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }

        session.setAttribute("usuarioId", usuario.getId());

        // El panel depende del rol guardado en la base de datos
        if (usuario.getRol() == Rol.COORDINADOR) {
            return "redirect:/panel/coordinador";
        }

        if (usuario.getRol() == Rol.DOCENTE) {
            return "redirect:/panel/docente";
        }

        model.addAttribute("error", "Tu cuenta no tiene un rol asignado");
        return "login";
    }

    // ---------- SIGNUP (único, con selector de rol) ----------

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "signup";
    }

    @PostMapping("/signup")
    public String registrar(@ModelAttribute("usuario") UsuarioModel usuario,
                            Model model,
                            RedirectAttributes ra) {

        try {
            usuarioServicio.registrarUsuario(usuario);
        } catch (IllegalArgumentException e) {
            // Se queda en el formulario y muestra el motivo (correo repetido, rol vacío...)
            model.addAttribute("error", e.getMessage());
            return "signup";
        }

        ra.addFlashAttribute("mensajeExito", "Cuenta creada. Ya puedes iniciar sesión.");
        return "redirect:/login";
    }
}
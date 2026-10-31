package com.example.atlas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.atlas.enums.Rol;
import com.example.atlas.modelo.UsuarioModel;
import com.example.atlas.servicio.DocenteServicio;
import com.example.atlas.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/panel/docente")
public class DocenteController {

    private final UsuarioServicio usuarioServicio;
    private final DocenteServicio docenteServicio;

    public DocenteController(UsuarioServicio usuarioServicio, DocenteServicio docenteServicio) {
        this.usuarioServicio = usuarioServicio;
        this.docenteServicio = docenteServicio;
    }

    @GetMapping
    public String panelDocente(HttpSession session, Model model) {

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/login";
        }

        UsuarioModel docente = usuarioServicio.buscarPorId(usuarioId);
        if (docente.getRol() != Rol.DOCENTE) {
            return "redirect:/login";
        }

        model.addAttribute("docente", docente);
        model.addAttribute("institucion", docente.getInstitucion());
        model.addAttribute("cursos", docenteServicio.cursosDelDocente(usuarioId));
        model.addAttribute("estudiantes", docenteServicio.estudiantesDelDocente(usuarioId));

        return "docente/panel-docente";
    }
}
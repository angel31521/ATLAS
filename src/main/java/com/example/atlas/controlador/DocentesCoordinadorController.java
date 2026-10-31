package com.example.atlas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.atlas.servicio.DocenteServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/panel/coordinador/docentes")
public class DocentesCoordinadorController {

    private final DocenteServicio docenteServicio;

    public DocentesCoordinadorController(DocenteServicio docenteServicio) {
        this.docenteServicio = docenteServicio;
    }

    @PostMapping
    public String agregar(@RequestParam String correo,
                        HttpSession session,
                        RedirectAttributes ra) {

        Long coordinadorId = (Long) session.getAttribute("usuarioId");

        try {
            docenteServicio.agregarPorCorreo(correo, coordinadorId);
            ra.addFlashAttribute("mensajeExito", "Docente agregado correctamente.");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("errorDocente", e.getMessage());
        }

        return "redirect:/panel/coordinador";
    }

    @PostMapping("/{id}/quitar")
    public String quitar(@PathVariable Long id,
                         HttpSession session,
                         RedirectAttributes ra) {

        Long coordinadorId = (Long) session.getAttribute("usuarioId");

        try {
            docenteServicio.quitar(id, coordinadorId);
            ra.addFlashAttribute("mensajeExito", "Docente quitado de la institución.");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("errorDocente", e.getMessage());
        }

        return "redirect:/panel/coordinador";
    }
}

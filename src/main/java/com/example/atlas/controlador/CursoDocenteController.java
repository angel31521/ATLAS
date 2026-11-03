package com.example.atlas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.atlas.servicio.CursoDocenteServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/panel/coordinador/cursos/{cursoId}/docentes")
public class CursoDocenteController {

    private final CursoDocenteServicio cursoDocenteServicio;

    public CursoDocenteController(CursoDocenteServicio cursoDocenteServicio) {
        this.cursoDocenteServicio = cursoDocenteServicio;
    }

    @PostMapping
    public String asignar(@PathVariable Long cursoId,
                          @RequestParam Long docenteId,
                          @RequestParam String cargo,
                          HttpSession session,
                          RedirectAttributes ra) {

        Long coordinadorId = (Long) session.getAttribute("usuarioId");

        try {
            cursoDocenteServicio.asignar(cursoId, docenteId, cargo, coordinadorId);
            ra.addFlashAttribute("mensajeExito", "Docente asignado al curso.");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("errorDocente", e.getMessage());
        }

        return "redirect:/panel/coordinador";
    }

    @PostMapping("/{asignacionId}/quitar")
    public String quitar(@PathVariable Long cursoId,
                         @PathVariable Long asignacionId,
                         HttpSession session,
                         RedirectAttributes ra) {

        Long coordinadorId = (Long) session.getAttribute("usuarioId");

        try {
            cursoDocenteServicio.quitar(cursoId, asignacionId, coordinadorId);
            ra.addFlashAttribute("mensajeExito", "Docente quitado del curso.");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("errorDocente", e.getMessage());
        }

        return "redirect:/panel/coordinador";
    }
}